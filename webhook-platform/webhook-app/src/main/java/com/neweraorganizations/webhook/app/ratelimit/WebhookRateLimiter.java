package com.neweraorganizations.webhook.app.ratelimit;

import com.neweraorganizations.webhook.app.config.WebhookRateLimitProperties;
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

    public boolean allow(String provider, String ip) {

        if (!properties.isEnabled()) {
            return true;
        }

        Integer limit = properties.getLimits().get(provider);
        if (limit == null) {
            return false; // unknown provider → deny
        }

        long now = Instant.now().toEpochMilli();
        String key = provider + ":" + ip;

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
                    "RateLimit check | provider={} | ip={} | count={}",
                    provider,
                    ip,
                    counter.count.get()
            );
            return counter.count.incrementAndGet() <= limit;
        }

    }
}
