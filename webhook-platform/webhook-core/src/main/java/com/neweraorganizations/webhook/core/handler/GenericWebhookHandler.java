package com.neweraorganizations.webhook.core.handler;

import org.springframework.stereotype.Component;

/**
 * Default webhook handler.
 * Can be used for testing or as a fallback.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class GenericWebhookHandler implements WebhookHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GenericWebhookHandler.class);

    @Override
    public String getProvider() {
        return "generic";
    }

    @Override
    public void handle(String eventType, String payload) {
        log.info("Handling webhook in GenericWebhookHandler");
        log.info("Event Type: {}", eventType);
        log.info("Payload: {}", payload);
    }
}
