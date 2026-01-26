package com.neweraorganizations.webhook.api.dto;

import java.time.Instant;
/**
 * Admin-facing webhook event representation.
 */
public class WebhookEventAdminDto {

    private String eventId;
    private String providerKey;
    private String eventType;
    private String status;
    private Instant receivedAt;

    public WebhookEventAdminDto(
            String eventId,
            String providerKey,
            String eventType,
            String status,
            Instant receivedAt
    ) {
        this.eventId = eventId;
        this.providerKey = providerKey;
        this.eventType = eventType;
        this.status = status;
        this.receivedAt = receivedAt;
    }

    public String getEventId() {
        return eventId;
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
