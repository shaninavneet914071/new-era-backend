package com.neweraorganizations.webhook.app.service;

import com.neweraorganizations.webhook.core.provider.ProviderContext;
import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import com.neweraorganizations.webhook.persistence.repository.WebhookEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WebhookIngestionService {

    private final WebhookEventRepository webhookEventRepository;

    public WebhookIngestionService(
            WebhookEventRepository webhookEventRepository
    ) {
        this.webhookEventRepository = webhookEventRepository;
    }

    @Transactional
    public WebhookEventEntity saveInitialEvent(
            ProviderContext provider,
            String eventId,
            String eventType,
            String payload,
            String status
    ) {
        WebhookEventEntity entity =
                new WebhookEventEntity(
                        provider.getProviderId(),
                        provider.getProviderKey(),
                        eventId,
                        eventType,
                        payload,
                        status
                );
            return webhookEventRepository.saveAndFlush(entity);


    }
}
