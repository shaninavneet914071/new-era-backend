package com.nsh.customerservice.config;


import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RealmEventsConfigRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminClientConfig {

    @Value("${keycloak.credentials.secret}")
    private String secretKey;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.auth-server-url}")
    private String authUrl;
    @Value("${keycloak.realm}")
    private String realm;
    private ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilderImpl();
    @Bean
    public Keycloak keycloak() {
        Keycloak keycloak = KeycloakBuilder.builder().
                serverUrl(authUrl).realm(realm).
                grantType(OAuth2Constants.CLIENT_CREDENTIALS).
                clientId(clientId).clientSecret(secretKey).
//                username("admin").password("admin").
                resteasyClient(
                        resteasyClientBuilder.connectionPoolSize(10).build())
                .build();
        return keycloak;
    }

    public UsersResource getUserResource() {
        return keycloak().realm(realm).users();
    }


    public RealmEventsConfigRepresentation getKeyCloakConfiguration() {
        return keycloak().realm(realm).getRealmEventsConfig();
    }

//    KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
//    KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
//    KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
//    AccessToken accessToken = session.getToken();
//    username = accessToken.getPreferredUsername();
//    emailID = accessToken.getEmail();
//    lastname = accessToken.getFamilyName();
//    firstname = accessToken.getGivenName();
//    realmName = accessToken.getIssuer();
//    Access realmAccess = accessToken.getRealmAccess();
//    roles = realmAccess.getRoles();



}