package com.neweraorganizations.webhook.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "webhook.rate-limit")
public class WebhookRateLimitProperties {

    private boolean enabled;
    private long windowMs;
    private Map<String, Integer> limits;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getWindowMs() {
        return windowMs;
    }

    public void setWindowMs(long windowMs) {
        this.windowMs = windowMs;
    }

    public Map<String, Integer> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, Integer> limits) {
        this.limits = limits;
    }
}
