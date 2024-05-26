package com.nsh.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    @ResponseBody
    @GetMapping("/test")
    public String test(){
        return "Hello sir the api controller is working fine!!";
    }
}
