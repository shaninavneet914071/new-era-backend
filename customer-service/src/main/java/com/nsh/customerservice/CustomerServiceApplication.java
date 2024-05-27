package com.nsh.customerservice;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(scanBasePackages = {"com.nsh.customerservice","com.nsh.customerservice.services"})
@EnableAutoConfiguration
@EnableWebSecurity
@EnableSpringDataWebSupport
@SecurityScheme(
	   name	= "Keycloak",
		scheme = "bearer",
		bearerFormat ="JWT",
		type = SecuritySchemeType.HTTP)
@EnableDiscoveryClient
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}
