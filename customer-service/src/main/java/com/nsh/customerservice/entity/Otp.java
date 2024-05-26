package com.nsh.customerservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "OTP")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {
    @Id
    @UuidGenerator
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;
    private String email;
    private String oneTimePassword;
    private Date requestedTime;
    private Date expiresIn;

    public Otp(String email, String oneTimePassword, Date requestedTime, Date expiresIn) {
        this.email = email;
        this.oneTimePassword = oneTimePassword;
        this.requestedTime = requestedTime;
        this.expiresIn = expiresIn;
    }
}
