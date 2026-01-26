package com.neweraorganizations.webhook.core.handler;

public interface WebhookHandler {

    /**
     * @return unique provider key (e.g. "razorpay", "github")
     * This is NOT user input — it's a system identifier.
     */
    String providerKey();

    /**
     * Handle incoming webhook event.
     */
    void handle(String eventType, String payload);
}
