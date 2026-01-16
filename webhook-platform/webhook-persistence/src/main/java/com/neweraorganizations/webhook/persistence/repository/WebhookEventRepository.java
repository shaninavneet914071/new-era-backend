package com.neweraorganizations.webhook.persistence.repository;

import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface WebhookEventRepository
        extends JpaRepository<WebhookEventEntity, Long> {

    Optional<WebhookEventEntity> findByEventId(String eventId);
    List<WebhookEventEntity> findByStatusNot(String status);

    List<WebhookEventEntity> findByStatusAndRetryCountLessThan(
            String status,
            int maxRetryCount
    );
    Page<WebhookEventEntity> findByStatus(
            String status,
            Pageable pageable
    );

    Page<WebhookEventEntity> findByProvider(
            String provider,
            Pageable pageable
    );

    Page<WebhookEventEntity> findByStatusAndProvider(
            String status,
            String provider,
            Pageable pageable
    );

    Optional<WebhookEventEntity> findByEventIdAndStatus(
            String eventId,
            String status
    );

    List<WebhookEventEntity> findByStatusAndReceivedAtBefore(
            String status,
            Instant cutoff
    );

}
