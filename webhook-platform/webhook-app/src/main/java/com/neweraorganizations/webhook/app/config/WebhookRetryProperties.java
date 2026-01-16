package com.neweraorganizations.webhook.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "webhook.retry")
public class WebhookRetryProperties {

    private boolean enabled;
    private long fixedDelayMs;
    private int maxAttempts;
    private long pendingTimeoutMs;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getFixedDelayMs() {
        return fixedDelayMs;
    }

    public void setFixedDelayMs(long fixedDelayMs) {
        this.fixedDelayMs = fixedDelayMs;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public long getPendingTimeoutMs() {
        return pendingTimeoutMs;
    }

    public void setPendingTimeoutMs(long pendingTimeoutMs) {
        this.pendingTimeoutMs = pendingTimeoutMs;
    }
}