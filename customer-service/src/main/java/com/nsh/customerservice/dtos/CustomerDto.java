package com.nsh.customerservice.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nsh.customerservice.dtos.address.AddressDto;
import com.nsh.customerservice.enums.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private UUID id;
    @NotEmpty(message = "Name must not be left empty")
    @Size(min = 4, max = 20, message = "Name size must be between 4-20 chars")
    private String firstName;
    private String lastName;
    private String email;
    @NotEmpty(message = "password is null")
    @Size(min = 4, max = 20, message = "Password size must be between 4-20 chars")
    @JsonIgnoreProperties
    private String password;
    private String keyCloakUserId;
    private boolean emailVerified;
    private boolean enabled;
    private Role role;
    private AddressDto address;
}
