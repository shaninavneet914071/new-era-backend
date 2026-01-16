package com.neweraorganizations.webhook.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.neweraorganizations.webhook")
@EntityScan("com.neweraorganizations.webhook.persistence.entity")
@EnableJpaRepositories("com.neweraorganizations.webhook.persistence.repository")
@EnableAsync
@EnableScheduling
public class WebhookApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookApplication.class, args);
    }
}
