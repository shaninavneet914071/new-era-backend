//package com.nsh.blog_rest_service.controller;
//
//import com.nsh.blog_rest_service.feignclient.CustomerFeignClient;
//import com.nsh.blog_rest_service.feignclient.response.CustomerDto;
//import com.nsh.blog_rest_service.payload.UserDto;
//import com.nsh.blog_rest_service.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@Slf4j
//@RequestMapping("/api/v1/post")
//public class PostController {
//    @Autowired
//    UserService userService;
//   private final CustomerFeignClient customerClient;
//
//    public PostController(CustomerFeignClient customerClient) {
//        this.customerClient = customerClient;
//    }
//
//
//    @GetMapping("/userData")
//    @Operation(summary = "Get Hello Message", responses = {
//            @ApiResponse(responseCode = "200", description = "Successful operation")
//    })
//    public ResponseEntity<CustomerDto> getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam(required = false) String email, @RequestParam(required = false) UUID customerId)
//    {
//        log.info("running to check the customer data");
//        return customerClient.find(email,customerId,token);
//    }
//    @PostMapping("/")
//    @Operation(summary = "Get Hello Message", responses = {
//            @ApiResponse(responseCode = "200", description = "Successful operation")
//    })
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//        UserDto createUserDto = this.userService.createUser(userDto,userDto.getId());
//        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/fetch")
//    @Operation(summary = "Get Hello Message", responses = {
//            @ApiResponse(responseCode = "200", description = "Successful operation")
//    })
//    public ResponseEntity<UserDto> fetchUsrData(@RequestParam UUID id) {
//        UserDto createUserDto = this.userService.getUserById(id);
//        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//    }
//}
