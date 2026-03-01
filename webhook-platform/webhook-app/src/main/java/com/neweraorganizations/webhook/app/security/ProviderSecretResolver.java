package com.neweraorganizations.webhook.app.security;

import com.neweraorganizations.webhook.core.provider.ProviderContext;
import com.neweraorganizations.webhook.persistence.entity.ProviderSecretEntity;
import com.neweraorganizations.webhook.persistence.repository.ProviderSecretRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderSecretResolver{

    private final ProviderSecretRepository providerSecretRepository;

    public ProviderSecretResolver(
            ProviderSecretRepository providerSecretRepository
    ) {
        this.providerSecretRepository = providerSecretRepository;
    }


    public List<String> resolveActiveSecrets(ProviderContext provider) {

        List<ProviderSecretEntity> secrets =
                providerSecretRepository.findByProvider_IdAndActiveTrue(
                        provider.getProviderId()
                );

        if (secrets.isEmpty()) {
            throw new IllegalStateException(
                    "No active secrets for provider: " + provider.getProviderKey()
            );
        }

        return secrets.stream()
                .map(ProviderSecretEntity::getSecret)
                .toList();
    }
}