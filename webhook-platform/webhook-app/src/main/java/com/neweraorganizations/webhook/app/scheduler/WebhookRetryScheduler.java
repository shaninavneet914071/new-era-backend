package com.neweraorganizations.webhook.app.scheduler;

import com.neweraorganizations.webhook.app.config.WebhookRetryProperties;
import com.neweraorganizations.webhook.app.service.WebhookReplayService;
import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import com.neweraorganizations.webhook.persistence.repository.WebhookEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class WebhookRetryScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(WebhookRetryScheduler.class);

    private final WebhookReplayService webhookReplayService;
    private final WebhookRetryProperties retryProperties;
    private final WebhookEventRepository webhookEventRepository;

    private final AtomicBoolean retryInProgress = new AtomicBoolean(false);

    public WebhookRetryScheduler(
            WebhookReplayService webhookReplayService,
            WebhookRetryProperties retryProperties, WebhookEventRepository webhookEventRepository
    ) {
        this.webhookReplayService = webhookReplayService;
        this.retryProperties = retryProperties;
        this.webhookEventRepository = webhookEventRepository;
    }
    /**
     * Retry failed webhook events periodically.
     * Runs every 60 seconds.
     */
//    @Scheduled(fixedDelay = 60_000)
    @Scheduled(fixedDelayString = "#{@webhookRetryProperties.fixedDelayMs}")
    public void retryFailedWebhooks() {
        if (!retryProperties.isEnabled()) {
            return;
        }
        // Prevent overlapping executions
        if (!retryInProgress.compareAndSet(false, true)) {
            log.warn("Retry job already running, skipping this cycle");
            return;
        }
        try {
            log.info("Scheduled webhook retry started");
            // 🔥 NEW: recover stuck async jobs
            recoverStuckPendingEvents();

            int replayedCount = webhookReplayService.replayFailedEvents();

            log.info("Scheduled webhook retry finished | replayedCount={}",
                    replayedCount);
        }finally {
            retryInProgress.set(false);
        }

    }

    private void recoverStuckPendingEvents() {

        Instant cutoff =
                Instant.now().minusMillis(retryProperties.getPendingTimeoutMs());

        List<WebhookEventEntity> stuckPending =
                webhookEventRepository.findByStatusAndReceivedAtBefore(
                        "PENDING",
                        cutoff
                );

        for (WebhookEventEntity event : stuckPending) {
            log.warn(
                    "Recovering stuck PENDING webhook | eventId={} | receivedAt={}",
                    event.getEventId(),
                    event.getReceivedAt()
            );

            event.setStatus("FAILED_PROCESSING");
            webhookEventRepository.save(event);
        }
    }
}
