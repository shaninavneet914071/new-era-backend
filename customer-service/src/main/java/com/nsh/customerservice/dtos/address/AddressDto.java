package com.nsh.customerservice.dtos.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDto {
    private UUID id;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String country;
}
