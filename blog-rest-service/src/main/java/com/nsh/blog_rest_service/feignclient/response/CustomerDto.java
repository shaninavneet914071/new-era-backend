package com.nsh.blog_rest_service.feignclient.response;


import com.nsh.blog_rest_service.feignclient.response.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String keyCloakUserId;
    private boolean emailVerified;
    private boolean enabled;
    private Role role;
    private AddressDto address;
}
