package com.nsh.customerservice.services.implementations;

import com.nsh.customerservice.config.KeycloakAdminClientConfig;
import com.nsh.customerservice.constantdata.AppConstant;
import com.nsh.customerservice.dao.CustomerRepo;
import com.nsh.customerservice.dtos.CustomerDto;
import com.nsh.customerservice.entity.Customer;
import com.nsh.customerservice.entity.KeycloakRequiredActions;
import com.nsh.customerservice.enums.Roles;
import com.nsh.customerservice.exceptionhandler.exceptions.CustomerAlreadyExist;
import com.nsh.customerservice.exceptionhandler.exceptions.NotFoundException;
import com.nsh.customerservice.keycloak.KeycloakAuthResponse;
import com.nsh.customerservice.keycloak.TokenAuth;
import com.nsh.customerservice.keycloak.service.KeycloakService;
import com.nsh.customerservice.services.CustomerMapper;
import com.nsh.customerservice.services.CustomerService;
import com.nsh.customerservice.util.TokenData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    KeycloakAdminClientConfig keycloakAdminClientConfig;
    @Autowired
    KeycloakService keycloakService;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ModelMapper customerMapper;
    @Autowired
    private ModelMapper customerCustomMapper;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.credentials.secret}")
    private String secretKey;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authUrl;
    @Value("${keycloak.credentials.provider}")
    private String provider;

    public KeycloakAuthResponse login(TokenAuth tokenParam) throws JSONException {
        Optional<Customer> customer = customerRepo.findByEmail(tokenParam.getEmail());
        if (customer.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        if (!isEmailVerified(tokenParam.getEmail())) {
            throw new NotFoundException("Email is not verified.Please check the mail containing verification link.");
        }
        return fetchToken(tokenParam);
    }

    public KeycloakAuthResponse fetchToken(TokenAuth auth) throws JSONException {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", AppConstant.CLIENT_SECRET);
        clientCredentials.put("grant_type", "password");
        clientCredentials.put("provider", provider);
        Configuration configuration =
                new Configuration(authUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        AccessTokenResponse response = new AccessTokenResponse();
        try {
            response = authzClient.obtainAccessToken(auth.getEmail(), auth.getPassword());
        } catch (Exception e) {
            JSONObject error = new JSONObject(e.getMessage());
            throw new JSONException(String.valueOf(error));
        }
        KeycloakAuthResponse.KeycloakAuthResponseBuilder keycloakResponse = KeycloakAuthResponse.builder();
        keycloakResponse.accessToken(response.getToken());
        keycloakResponse.expiresIn(response.getExpiresIn());
        keycloakResponse.refreshExpiresIn(response.getRefreshExpiresIn());
        keycloakResponse.refreshToken(response.getRefreshToken());
        keycloakResponse.tokenType(response.getTokenType());
        keycloakResponse.sessionState(response.getSessionState());
        keycloakResponse.scope(response.getScope()).build();
        return keycloakResponse.build();
    }

    //public void sendEmailVerification(String userId){
//        Keycloak keycloak = keycloakAdminClientConfig.keycloak();
//    RealmResource resource = keycloak.realm(realm);
//    keycloak.tokenManager().getAccessToken();
//    Optional<UserRepresentation> userRepresentation = resource.users().search(userId).stream().findFirst();
//
//       if(userRepresentation.get().isEmailVerified()){
//           throw new CustomerAlreadyExist("email already verified");
//       }
//    resource.users().get(userId).sendVerifyEmail();
//}
    @Override
    public void sendEmailVerification(String userId) {
        keycloakService.sendEmailVerification(userId);
    }

    public String addUser(CustomerDto userDTO) {
        if (customerRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new CustomerAlreadyExist("Customer already exist with this email");
        }
        Keycloak keycloak = keycloakAdminClientConfig.keycloak();
        keycloak.tokenManager().getAccessToken();
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRequiredActions(Collections.singletonList(KeycloakRequiredActions.VERIFY_EMAIL.name()));
        user.singleAttribute("UserType", String.valueOf(Roles.USER));
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        RealmResource resource = keycloak.realm(realm);
        UsersResource usersResource = resource.users();
        List<UserRepresentation> existingUsers = usersResource.search(userDTO.getEmail(), true);
        if (!existingUsers.isEmpty()) {
            // User with the same email already exists
            return "User with this email already exists.";
        }
        Response response = usersResource.create(user);
//        Logger.("Response |  Status: {} | Status Info: {}", response.getStatus(), response.getStatusInfo());
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
//            log.info("Created userId {}", userId);
            // create password credential
            CredentialRepresentation passwordCred = Credentials.createPasswordCredentials(userDTO.getPassword());
            UserResource userResource = usersResource.get(userId);

            // Set password credential
            userResource.resetPassword(passwordCred);

            // Get realm role app-user
            RoleRepresentation realmRoleUser = resource.roles().get("USER").toRepresentation();
            // Assign realm role app-user to user
            userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));

            userResource.sendVerifyEmail();
            System.out.println("Verification email has been sent !!");
            Customer customer = CustomerMapper.dtoToCustomer(userDTO, userId);
            customer.setRole(Roles.valueOf(user.firstAttribute("UserType")));
            customerRepo.save(customer);

            return "User has been created.Please verify the email to log in";
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean isEmailVerified(String email) {
        return keycloakService.isEmailVerified(email);
    }

    @Override
    public void sendVerificationEmail(String email) {
        keycloakService.sendVerificationEmail(email);
    }


    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Customer customer = customerRepo.findById(customerDto.getId()).get();
        updateMyObject(customerDto, customer);
        return customerMapper.map(customerRepo.save(customer), CustomerDto.class);
    }

    @Override
    public void delete(UUID id) {
        customerRepo.deleteById(id);
    }

    public CustomerDto getById(UUID id) {

        return customerMapper.map(customerRepo.findById(id).get(), CustomerDto.class);
    }

    public List<CustomerDto> getAll() {
        return customerRepo.findAll().stream().map(a -> customerMapper.map(a, CustomerDto.class)).toList();
    }

    private void updateMyObject(CustomerDto sourceObject, Customer destinationObject) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        mapper.map(sourceObject, destinationObject);
    }

    @Override
    public CustomerDto getUserByEmail(String email, UUID custId) {
//        List<UserRepresentation> users = keycloakAdminClientConfig.getUserResource().search(email);
//        return users.isEmpty() ? null : users.get(0);
        String requiredMail;
        Optional<Customer> customer = Optional.empty();
        if(custId!=null){

            customer =customerRepo.findById(custId);
        }
        if(request!=null){
            requiredMail = TokenData.getEmailFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        }
        else if (email!=null){
            requiredMail =email;
        }
        else{
            throw new IllegalArgumentException("Please provide the mail to see your profile");
        }
        if (keycloakService.getUserByEmail(email) != null) {
           customer = customerRepo.findByEmail(requiredMail);
        }
            if(customer.isEmpty()){
                throw new NotFoundException("Customer not found !!!");
            }

        return customerMapper.map(customerRepo.findByEmail(requiredMail), CustomerDto.class);

    }

    //    UserRepresentation user = new AbstractUserRepresentation();
//
//    public AbstractUserRepresentation getUser() {
//        return user;
    static class Credentials {

        public static CredentialRepresentation createPasswordCredentials(String password) {
            CredentialRepresentation passwordCredentials = new CredentialRepresentation();
            passwordCredentials.setTemporary(false);
            passwordCredentials.setType(CredentialRepresentation.PASSWORD);
            passwordCredentials.setValue(password);
            return passwordCredentials;
        }
    }
}

