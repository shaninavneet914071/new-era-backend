//package com.nsh.blog_rest_service.configuration;
//
//import jakarta.ws.rs.HttpMethod;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
////@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//    public static final String[] PUBLIC_URLS = {
//            "/api/v1/auth/**", "/v3/api-docs", "/swagger-resources/**",
//            "/swagger-ui", "/webjars/**", "/v2/api-docs", "/api/v1/users/userData"
//    };
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers(PUBLIC_URLS).permitAll()
//                            .requestMatchers(HttpMethod.GET).permitAll()
//                            .anyRequest().authenticated();
//                }).build();
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//}
