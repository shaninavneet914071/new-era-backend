package com.neweraorganizations.webhook.app.ratelimit;

import com.neweraorganizations.webhook.app.config.WebhookRateLimitProperties;
import com.neweraorganizations.webhook.core.provider.ProviderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WebhookRateLimiter {

    private final Logger log = LoggerFactory.getLogger(WebhookRateLimiter.class);

    private final WebhookRateLimitProperties properties;

    private static class Counter {
        AtomicInteger count = new AtomicInteger(0);
        long windowStart;
    }

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();

    public WebhookRateLimiter(WebhookRateLimitProperties properties) {
        this.properties = properties;
    }

    public boolean allow(ProviderContext provider, String ip) {

        if (!properties.isEnabled()) {
            return true;
        }

        Integer limit = properties.getLimits().get(provider.getProviderId());
        if (limit == null) {
            return false; // unknown provider.getProviderId() → deny
        }

        long now = Instant.now().toEpochMilli();
        String key = provider.getProviderId() + ":" + ip;

        Counter counter = counters.computeIfAbsent(key, k -> {
            Counter c = new Counter();
            c.windowStart = now;
            return c;
        });

        synchronized (counter) {
            if (now - counter.windowStart > properties.getWindowMs()) {
                counter.windowStart = now;
                counter.count.set(0);
            }
            log.info(
                    "RateLimit check | provider.getProviderId()={} | ip={} | count={}",
                    provider.getProviderId(),
                    ip,
                    counter.count.get()
            );
            return counter.count.incrementAndGet() <= limit;
        }

    }
}
