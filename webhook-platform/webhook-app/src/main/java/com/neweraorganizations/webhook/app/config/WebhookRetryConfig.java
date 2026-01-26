package com.neweraorganizations.webhook.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebhookRetryConfig {

    @Bean(name = "webhookRetryProperties")
    public WebhookRetryProperties webhookRetryProperties() {
        return new WebhookRetryProperties();
    }
}
