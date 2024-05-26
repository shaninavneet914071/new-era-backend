package com.nsh.customerservice.entity;


import com.nsh.customerservice.entity.address.Address;
import com.nsh.customerservice.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String keyCloakUserId;
    private boolean emailVerified;
    private boolean enabled;
    @Enumerated(EnumType.ORDINAL)
    private Roles role;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Address address;
}
