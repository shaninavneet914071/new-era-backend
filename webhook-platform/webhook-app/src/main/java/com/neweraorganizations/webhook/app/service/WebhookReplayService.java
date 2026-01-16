package com.neweraorganizations.webhook.app.service;

import com.neweraorganizations.webhook.app.config.WebhookRetryProperties;
import com.neweraorganizations.webhook.core.router.WebhookRouter;
import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import com.neweraorganizations.webhook.persistence.repository.WebhookEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class WebhookReplayService {

    private static final Logger log =
            LoggerFactory.getLogger(WebhookReplayService.class);

    private final WebhookEventRepository webhookEventRepository;
    private final WebhookRouter webhookRouter;
    private final WebhookAsyncProcessor webhookAsyncProcessor;

    private final WebhookRetryProperties retryProperties;

    public WebhookReplayService(
            WebhookEventRepository webhookEventRepository,
            WebhookRouter webhookRouter, WebhookAsyncProcessor webhookAsyncProcessor, WebhookRetryProperties retryProperties
    ) {
        this.webhookEventRepository = webhookEventRepository;
        this.webhookRouter = webhookRouter;
        this.webhookAsyncProcessor = webhookAsyncProcessor;
        this.retryProperties = retryProperties;
    }

    /**
     * Replay all webhook events that are not SUCCESS.
     */
    @Transactional
    public int replayFailedEvents() {

        List<WebhookEventEntity> failedEvents =
                webhookEventRepository.findByStatusAndRetryCountLessThan(
                        "FAILED_PROCESSING",
                        retryProperties.getMaxAttempts()
                );

        int replayedCount = 0;

        for (WebhookEventEntity event : failedEvents) {
            try {
                log.info("Replaying webhook | eventId={} | status={}",
                        event.getEventId(), event.getRetryCount() + 1);

                event.setRetryCount(event.getRetryCount() + 1);
                event.setLastRetryAt(Instant.now());
                webhookEventRepository.save(event);
                event.setRetryCount(event.getRetryCount() + 1);
                event.setLastRetryAt(Instant.now());
                event.setStatus("FAILED_PROCESSING");
                webhookEventRepository.save(event);

                webhookAsyncProcessor.processAsync(event.getEventId());
                webhookEventRepository.save(event);
                replayedCount++;

            } catch (Exception ex) {
                log.error("Retry failed | eventId={} | attempt={}",
                        event.getEventId(), event.getRetryCount(), ex);

                if (event.getRetryCount() >= retryProperties.getMaxAttempts()) {
                    event.setStatus("DEAD_LETTER");
                }

                webhookEventRepository.save(event);
            }
        }

        return replayedCount;
    }

    @Transactional
    public void replayDeadLetterEvent(String eventId) {

        WebhookEventEntity event =
                webhookEventRepository
                        .findByEventIdAndStatus(eventId, "DEAD_LETTER")
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Dead-letter event not found: " + eventId
                                )
                        );

        log.info("Admin replay of dead-letter webhook | eventId={}", eventId);

        // reset retry state
        event.setRetryCount(0);
        event.setLastRetryAt(null);
        event.setStatus("FAILED_PROCESSING");

        webhookEventRepository.save(event);

        // async reprocessing
        webhookAsyncProcessor.processAsync(event.getEventId());
    }
}
