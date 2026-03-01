package com.neweraorganizations.webhook.api.dto;

import java.time.Instant;
/**
 * Admin-facing webhook event representation.
 */
public class WebhookEventAdminDto {

    private final String eventId;
    private final Long providerId;
    private final String providerKey;
    private final String eventType;
    private final String status;
    private final Instant receivedAt;

    public WebhookEventAdminDto(
            String eventId,
            Long providerId,
            String providerKey,
            String eventType,
            String status,
            Instant receivedAt
    ) {
        this.eventId = eventId;
        this.providerId = providerId;
        this.providerKey = providerKey;
        this.eventType = eventType;
        this.status = status;
        this.receivedAt = receivedAt;
    }

    public String getEventId() {
        return eventId;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public String getEventType() {
        return eventType;
    }

    public String getStatus() {
        return status;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}
