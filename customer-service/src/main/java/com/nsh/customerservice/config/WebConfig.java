package com.nsh.customerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Specify the URL pattern to allow CORS for
                .allowedOriginPatterns("http://localhost:3000/**") // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Allow specific HTTP methods
//                .allowCredentials(true); // Allow sending cookies with the request
    }
}