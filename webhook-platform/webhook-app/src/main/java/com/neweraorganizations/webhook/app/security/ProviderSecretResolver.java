package com.neweraorganizations.webhook.app.security;

import com.neweraorganizations.webhook.app.config.WebhookProviderProperties;
import org.springframework.stereotype.Service;

@Service
public class ProviderSecretResolver {

    private final WebhookProviderProperties properties;

    public ProviderSecretResolver(WebhookProviderProperties properties) {
        this.properties = properties;
    }

    public String resolveSecret(String provider) {

        if (properties.getProviders() == null ||
                !properties.getProviders().containsKey(provider)) {
            throw new IllegalArgumentException(
                    "Unknown webhook provider: " + provider
            );
        }

        return properties
                .getProviders()
                .get(provider)
                .getSecret();
    }
}