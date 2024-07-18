package com.nsh.customerservice.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenAuth {
    @JsonProperty("username")
    private String email;
    @JsonProperty("password")
    private String password;
}
