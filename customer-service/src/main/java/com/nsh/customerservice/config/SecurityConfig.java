package com.nsh.customerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
//    @Autowired
//    JwtAuthConverter jwtAuthConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .cors(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers(HttpMethod.GET,"/customer/welcome","/api/v1/customer/register","/api/v1/customer/login").permitAll()
                    .requestMatchers(HttpMethod.GET,"/customer/fetchAll").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/test/customer").permitAll()
                    .anyRequest().permitAll();

        });
//        http.oauth2ResourceServer(t-> t.jwt(configurer-> configurer.jwtAuthenticationConverter(jwtAuthConverter)));
//        http.oauth2ResourceServer(t-> t.jwt(Customizer.withDefaults()));
        http.sessionManagement(t-> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity(){
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler= new DefaultMethodSecurityExpressionHandler();
defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
return defaultMethodSecurityExpressionHandler;

    }

    @Bean
    public JwtAuthenticationConverter con(){
        JwtAuthenticationConverter c= new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
        cv.setAuthorityPrefix("");
        cv.setAuthoritiesClaimName("roles");
        c.setJwtGrantedAuthoritiesConverter(cv);
        return c;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); //allows React to access the API from origin on port 3000. Change accordingly
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
