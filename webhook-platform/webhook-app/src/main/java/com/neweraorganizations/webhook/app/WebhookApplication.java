package com.neweraorganizations.webhook.app;
import com.neweraorganizations.webhook.app.config.JwtProperties;
import com.neweraorganizations.webhook.app.config.WebhookProviderProperties;
import com.neweraorganizations.webhook.app.config.WebhookRateLimitProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication(scanBasePackages = "com.neweraorganizations.webhook")
@EntityScan("com.neweraorganizations.webhook.persistence.entity")
@EnableJpaRepositories("com.neweraorganizations.webhook.persistence.repository")
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties({JwtProperties.class, WebhookProviderProperties.class,WebhookRateLimitProperties.class})
public class WebhookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookApplication.class, args);
        String token = Jwts.builder()
                .claim("role", "ADMIN")
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(
                        Keys.hmacShaKeyFor("this-is-a-very-long-and-secure-admin-jwt-secret-key-32bytes+".getBytes())
                )
                .compact();

        System.out.println(token);

    }
}
