package com.nsh.blog_rest_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BlogRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogRestServiceApplication.class, args);
	}

}
