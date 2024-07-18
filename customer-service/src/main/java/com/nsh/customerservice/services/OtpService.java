package com.nsh.customerservice.services;

import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    void sendOtp(String email);

    String verifyEmail(String email, String otp);
}