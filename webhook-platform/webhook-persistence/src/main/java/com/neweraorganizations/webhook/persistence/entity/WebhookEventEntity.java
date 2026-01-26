package com.neweraorganizations.webhook.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "webhook_events")
public class WebhookEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_id", nullable = false)
    private Long providerId;


    @Column(name = "event_id", nullable = false, unique = true)
    private String eventId;

    @Column(name = "event_type")
    private String eventType;

    @Lob
    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "retry_count", nullable = false)
    private int retryCount = 0;

    @Column(name = "last_retry_at")
    private Instant lastRetryAt;

    protected WebhookEventEntity() {
        // JPA
    }

    public WebhookEventEntity(
            Long providerId,
            String eventId,
            String eventType,
            String payload,
            String status
    ) {
        this.providerId = providerId;
        this.eventId = eventId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = status;
        this.receivedAt = Instant.now();
    }

    // Getters only (immutability-friendly)

    public Long getId() {
        return id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
    public int getRetryCount() {
        return retryCount;
    }

    public Instant getLastRetryAt() {
        return lastRetryAt;
    }

    /* ===== GETTERS ===== */

    /* ===== SETTERS (ONLY WHAT IS ALLOWED TO CHANGE) ===== */

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setLastRetryAt(Instant lastRetryAt) {
        this.lastRetryAt = lastRetryAt;
    }
}
