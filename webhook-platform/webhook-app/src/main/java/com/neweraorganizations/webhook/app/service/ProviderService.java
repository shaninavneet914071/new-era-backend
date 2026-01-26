package com.neweraorganizations.webhook.app.service;

import com.neweraorganizations.webhook.core.provider.ProviderContext;
import com.neweraorganizations.webhook.persistence.entity.ProviderEntity;
import com.neweraorganizations.webhook.persistence.repository.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public ProviderContext getActiveProvider(String providerKey) {
        ProviderEntity providerEntity =
                providerRepository.findByProviderKey(providerKey)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Unknown provider"));

        if (!"ACTIVE".equals(providerEntity.getStatus())) {
            throw new IllegalStateException("Provider not active");
        }
        return new ProviderContext(
                providerEntity.getId(),
                providerEntity.getProviderKey(),
                providerEntity.getStatus()
        );
    }
}
