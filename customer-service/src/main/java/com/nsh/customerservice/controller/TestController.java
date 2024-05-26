package com.nsh.customerservice.controller;

import com.nsh.customerservice.services.CustomerService;
import com.nsh.customerservice.services.implementations.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test/customer")
@SecurityRequirement(name = "Keycloak")
public class TestController {
    @Autowired
    private EmailService mail;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/welcome")
    @ResponseBody
    public String hello() {
        return "hello world !!";
    }
    @PostMapping("/verify")
    public ResponseEntity<String> addUser(@RequestParam String userId) {
            customerService.sendEmailVerification(userId);
        return new ResponseEntity<String>("Verification link sent .", HttpStatusCode.valueOf(200));
    }
    @PostMapping("sendMail")
    public  ResponseEntity<String> sendMail() {
        mail.sendEmail();
        return new ResponseEntity<>("send mail",HttpStatusCode.valueOf(200));
    }
    @PostMapping("send-verification")
    public  ResponseEntity<String> sendMail(@RequestParam String email) {
        customerService.sendVerificationEmail(email);
        return new ResponseEntity<>("sent mail",HttpStatusCode.valueOf(200));
    }
    @GetMapping("check-verification")
    public  ResponseEntity<String> check(@RequestParam String email) {
       ;
        return new ResponseEntity<>("body :"+ customerService.isEmailVerified(email),HttpStatusCode.valueOf(200));
    }
}
//jcfl gkka pmpl tgge