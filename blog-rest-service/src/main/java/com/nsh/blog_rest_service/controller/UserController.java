package com.nsh.blog_rest_service.controller;

import com.nsh.blog_rest_service.feignclient.CustomerFeignClient;
import com.nsh.blog_rest_service.payload.UserDto;
import com.nsh.blog_rest_service.response.UserResponse;
import com.nsh.blog_rest_service.service.UserService;
import com.nsh.blog_rest_service.util.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    UserService userService;

    private final HttpServletRequest request;
   private final CustomerFeignClient customerClient;

    public UserController(HttpServletRequest request, CustomerFeignClient customerClient) {
        this.request = request;
        this.customerClient = customerClient;
    }

    @GetMapping("/userId")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<UUID> getProfile()
    {
        log.info("running to check the customer data");
        return new ResponseEntity<>(TokenUtil.getUserIdFromToken(TokenUtil.parseHeaderToken(request.getHeader(HttpHeaders.AUTHORIZATION))),HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDto userDto) {
        UserResponse createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
}
