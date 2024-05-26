package com.nsh.customerservice.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenAuth{
    @JsonProperty("username")
    private String email;
    @JsonProperty("password")
    private String password;
}
