package com.nsh.customerservice.services;

import com.nsh.customerservice.keycloak.KeycloakAuthResponse;
import com.nsh.customerservice.keycloak.TokenAuth;
import com.nsh.customerservice.dtos.CustomerDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
//    boolean isEmailVerified(String email);

//    void sendVerificationEmail(String email);

    CustomerDto getUserByEmail(String email, UUID customerId);

    boolean isEmailVerified(String email);
    void sendVerificationEmail(String email);
    CustomerDto update(CustomerDto customerDto);
    void delete(UUID id);
    CustomerDto getById(UUID id);
    List<CustomerDto> getAll();
    void sendEmailVerification(String userId);
    String addUser(CustomerDto userDTO);
   KeycloakAuthResponse login(TokenAuth auth) throws JSONException;
}
