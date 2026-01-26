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

    private static final Logger log =
            LoggerFactory.getLogger(WebhookRouter.class);

    private final Map<String, WebhookHandler> handlerMap = new HashMap<>();

    public WebhookRouter(List<WebhookHandler> handlers) {
        for (WebhookHandler handler : handlers) {
            handlerMap.put(handler.providerKey(), handler);
        }
    }

    /**
     * Routes webhook to the correct handler.
     */
    public void route(String providerKey, String eventType, String payload) {

        WebhookHandler handler = handlerMap.get(providerKey);

        if (handler == null) {
            log.warn("No webhook handler registered | providerKey={}", providerKey);
            return;
        }

        log.info("Routing webhook | providerKey={} | eventType={}",
                providerKey, eventType);

        handler.handle(eventType, payload);
    }
}
