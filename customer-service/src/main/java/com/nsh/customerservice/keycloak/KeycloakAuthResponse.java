package com.nsh.customerservice.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakAuthResponse {
    //    @JsonProperty("access_token")
    private String accessToken;

    //    @JsonProperty("expires_in")
    private Long expiresIn;

    //    @JsonProperty("refresh_expires_in")
    private Long refreshExpiresIn;

    //    @JsonProperty("refresh_token")
    private String refreshToken;

    //    @JsonProperty("token_type")
    private String tokenType;

    //    @JsonProperty("session_state")
    private String sessionState;

    //    @JsonProperty("scope")
    private String scope;
}
