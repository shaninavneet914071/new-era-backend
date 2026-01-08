package com.neweraorganizations.webhook.core.handler;

/**
 * Contract for all webhook providers.
 */
public interface WebhookHandler {

    /**
     * @return provider name (razorpay, github, stripe, etc.)
     */
    String getProvider();

    /**
     * Handle incoming webhook event.
     *
     * @param eventType event type
     * @param payload   raw webhook payload
     */
    void handle(String eventType, String payload);
}
