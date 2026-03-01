package com.neweraorganizations.webhook.app.service;

import com.neweraorganizations.webhook.core.router.WebhookRouter;
import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import com.neweraorganizations.webhook.persistence.repository.WebhookEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WebhookAsyncProcessor {

    private static final Logger log =
            LoggerFactory.getLogger(WebhookAsyncProcessor.class);

    private final WebhookRouter webhookRouter;
    private final WebhookEventRepository webhookEventRepository;

    public WebhookAsyncProcessor(
            WebhookRouter webhookRouter,
            WebhookEventRepository webhookEventRepository
    ) {
        this.webhookRouter = webhookRouter;
        this.webhookEventRepository = webhookEventRepository;
    }

    /**
     * Process webhook asynchronously.
     */
    @Transactional
    @Async
    public void processAsync(String eventId) {
        WebhookEventEntity event =
                webhookEventRepository.findByEventId(eventId)
                        .orElseThrow();
        try {
            log.info("Async processing webhook | eventId={}", eventId);

            webhookRouter.route(
                    event.getProviderKey(),
                    event.getEventType(),
                    event.getPayload()
            );

            event.setStatus("SUCCESS");
            webhookEventRepository.save(event);
            log.info("Async webhook processed successfully | eventId={}",
                    event.getEventId());

        } catch (Exception ex) {
            log.error("Async webhook processing failed | eventId={}",
                    eventId, ex);

            event.setStatus("FAILED_PROCESSING");
            event.setRetryCount(0);
            webhookEventRepository.save(event);
        }
    }
}
