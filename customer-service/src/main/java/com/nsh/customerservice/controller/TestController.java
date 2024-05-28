package com.nsh.customerservice.controller;

import com.nsh.customerservice.constantdata.AppConstant;
import com.nsh.customerservice.services.CustomerService;
import com.nsh.customerservice.services.implementations.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//TODO (Practice) create @annotation
@RestController
@RequestMapping("/api/v1/test/customer")
@SecurityRequirement(name = "Keycloak")
@Validated
public class TestController {
    @Autowired
    private EmailService mail;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/welcome")
    public String hello() {
        return "hello world !!";
    }
    @PostMapping("/verify")
    public ResponseEntity<String> addUser(@RequestParam String userId) {
            customerService.sendEmailVerification(userId);
        return new ResponseEntity<String>("Verification link sent .", HttpStatus.OK);
    }
    @PostMapping("sendMail")
    public ResponseEntity<String> sendTestMail() {
        mail.sendEmail();
        return new ResponseEntity<>("send mail", HttpStatus.OK);
    }

    @PostMapping("/send-verification")
    public ResponseEntity<String> sendMail(@RequestParam @Email(regexp = AppConstant.EMAIL_REGX) String email) {
        customerService.sendVerificationEmail(email);
        return new ResponseEntity<>("sent mail", HttpStatus.OK);
    }
    @GetMapping("check-verification")
    public  ResponseEntity<String> check(@RequestParam String email) {
        return new ResponseEntity<>("body :" + customerService.isEmailVerified(email), HttpStatus.OK);
    }
}