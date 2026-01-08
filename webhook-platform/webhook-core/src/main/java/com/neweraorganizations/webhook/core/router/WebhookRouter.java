package com.neweraorganizations.webhook.core.router;

import com.neweraorganizations.webhook.core.handler.WebhookHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WebhookRouter {

    private final Map<String, WebhookHandler> handlerMap = new HashMap<>();
     private static final Logger log = LoggerFactory.getLogger(WebhookRouter.class);
    public WebhookRouter(List<WebhookHandler> handlers) {
        for (WebhookHandler handler : handlers) {
            handlerMap.put(handler.getProvider().toLowerCase(), handler);
        }
    }

    /**
     * Routes webhook payload to appropriate provider handler.
     *
     * @param provider  the webhook provider (razorpay, github, stripe, etc.)
     * @param eventType the event type (payment.success, push, etc.)
     * @param payload   raw webhook payload
     */
    public void route(String provider, String eventType, String payload) {

        WebhookHandler handler =
                handlerMap.getOrDefault(provider.toLowerCase(), null);
        if (handler == null) {
            log.warn("No handler found for provider: {}", provider);
            return;
        }
        log.info("Routing webhook | provider={} | event={}", provider, eventType);
        handler.handle(eventType, payload);
    }
}
