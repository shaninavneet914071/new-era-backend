package com.nsh.customerservice.controller;

import com.nsh.customerservice.keycloak.KeycloakAuthResponse;
import com.nsh.customerservice.keycloak.TokenAuth;
import com.nsh.customerservice.dtos.CustomerDto;
import com.nsh.customerservice.services.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@SecurityRequirement(name = "Keycloak")
public class CustomerController {
    @Autowired
    private CustomerService service;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/welcome")
    @ResponseBody
    public String hello() {
        return "hello world !!";
    }
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<String>(service.addUser(customerDto), HttpStatusCode.valueOf(200));
    }
    @PostMapping("/login")
    public ResponseEntity<KeycloakAuthResponse> loginUser(@RequestBody TokenAuth tokeParam) throws JSONException {
        return new ResponseEntity<KeycloakAuthResponse>(service.login(tokeParam), HttpStatusCode.valueOf(200));
    }


    @PutMapping("/update")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customer) {
        return new ResponseEntity<CustomerDto>(service.update(customer), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomerDto> fetchCustomerData(@RequestParam UUID customerId) {
        return new ResponseEntity<CustomerDto>(service.getById(customerId), HttpStatusCode.valueOf(200));
    }


    @GetMapping("/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<CustomerDto> find(@RequestParam(required = false) String email, @RequestParam(required = false) UUID customerId) {
        return new ResponseEntity<>(service.getUserByEmail(email,customerId),HttpStatus.OK);
    }

    @GetMapping("/fetchAll")
    @CrossOrigin(origins = { "*" })
    public ResponseEntity<List<CustomerDto>> fetchAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomer(@RequestParam UUID id) {
        String deleteStatus;
        try {
            service.delete(id);
            deleteStatus = "delete successful";
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<String>(deleteStatus, HttpStatus.NO_CONTENT);
    }
}

