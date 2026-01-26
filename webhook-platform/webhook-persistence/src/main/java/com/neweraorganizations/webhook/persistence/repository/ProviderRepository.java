package com.neweraorganizations.webhook.persistence.repository;

import com.neweraorganizations.webhook.persistence.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository
        extends JpaRepository<ProviderEntity, Long> {

    Optional<ProviderEntity> findByProviderKey(String providerKey);
}
