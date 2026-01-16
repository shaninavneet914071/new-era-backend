package com.neweraorganizations.webhook.api.dto;

import java.time.Instant;

public class WebhookEventAdminDto {

    private String eventId;
    private String provider;
    private String eventType;
    private String status;
    private Instant receivedAt;

    public WebhookEventAdminDto(
            String eventId,
            String provider,
            String eventType,
            String status,
            Instant receivedAt
    ) {
        this.eventId = eventId;
        this.provider = provider;
        this.eventType = eventType;
        this.status = status;
        this.receivedAt = receivedAt;
    }

    public String getEventId() {
        return eventId;
    }

    public String getProvider() {
        return provider;
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
