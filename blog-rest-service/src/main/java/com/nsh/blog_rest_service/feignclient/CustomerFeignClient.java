package com.nsh.blog_rest_service.feignclient;

import com.nsh.blog_rest_service.feignclient.response.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
@FeignClient(name = "CUSTOMER-SERVICE", url = "http://localhost:8083")
public interface CustomerFeignClient {
    @GetMapping("/customer-service/api/v1/customer/profile")
    ResponseEntity<CustomerDto> find(@RequestParam(required = false) String email, @RequestParam(required = false) UUID customerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
