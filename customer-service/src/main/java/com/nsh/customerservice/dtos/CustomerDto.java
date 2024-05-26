package com.nsh.customerservice.dtos;


import com.nsh.customerservice.dtos.address.AddressDto;
import com.nsh.customerservice.enums.Roles;
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
    private Roles role;
    private AddressDto address;
}
