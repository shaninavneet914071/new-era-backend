package com.neweraorganizations.webhook.app.config;

import com.neweraorganizations.webhook.app.security.JwtAuthFilter;
import com.neweraorganizations.webhook.app.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtUtil jwtUtil
    ) throws Exception {

        http
            .csrf(csrf -> csrf.disable() /*csrf.ignoringRequestMatchers("/h2-console/**")*/)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/webhook/**").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                new JwtAuthFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
