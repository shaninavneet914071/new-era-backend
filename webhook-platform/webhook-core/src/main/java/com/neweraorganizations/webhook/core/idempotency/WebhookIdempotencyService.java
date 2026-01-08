package com.neweraorganizations.webhook.core.idempotency;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebhookIdempotencyService {

    // Thread-safe in-memory store
    private final Set<String> processedEventIds =
            ConcurrentHashMap.newKeySet();

    /**
     * Checks whether an event has already been processed.
     *
     * @param eventId unique webhook event id
     * @return true if already processed, false otherwise
     */
    public boolean isDuplicate(String eventId) {
        return processedEventIds.contains(eventId);
    }

    /**
     * Marks an event as processed.
     *
     * @param eventId unique webhook event id
     */
    public void markProcessed(String eventId) {
        processedEventIds.add(eventId);
    }
}
