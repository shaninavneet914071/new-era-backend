package com.neweraorganizations.webhook.persistence.repository;

import com.neweraorganizations.webhook.persistence.entity.ProviderSecretEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderSecretRepository
        extends JpaRepository<ProviderSecretEntity, Long> {

    List<ProviderSecretEntity> findByProviderIdAndActiveTrue(Long providerId);
}
