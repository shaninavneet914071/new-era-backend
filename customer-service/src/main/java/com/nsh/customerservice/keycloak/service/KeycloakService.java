package com.nsh.customerservice.keycloak.service;

import com.nsh.customerservice.config.KeycloakAdminClientConfig;
import com.nsh.customerservice.exceptionhandler.exceptions.CustomerAlreadyExist;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KeycloakService {
    @Autowired
    private KeycloakAdminClientConfig keycloakAdminClientConfig;
    public UserRepresentation getUserByEmail(String email) {
        List<UserRepresentation> users = keycloakAdminClientConfig.getUserResource().search(email);
        return users.isEmpty() ? null : users.get(0);
    }
    public void sendEmailVerification(String userId) {
        UsersResource usersResource = keycloakAdminClientConfig.getUserResource();
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();
        if(userRepresentation.isEmailVerified()){
            throw new CustomerAlreadyExist(userRepresentation.getEmail()+" email already verified");
        }
        usersResource.get(userId).sendVerifyEmail();
    }
    public boolean isEmailVerified(String email) {
        UserRepresentation user = getUserByEmail(email);
//        UserRepresentation user = keycloakAdminClientConfig.getUserResource().get(userId).toRepresentation();
        return user.isEmailVerified();
    }
    public void sendVerificationEmail(String email) {
        UserRepresentation user = getUserByEmail(email);
        keycloakAdminClientConfig.getUserResource().get(user.getId()).sendVerifyEmail();
    }

}
