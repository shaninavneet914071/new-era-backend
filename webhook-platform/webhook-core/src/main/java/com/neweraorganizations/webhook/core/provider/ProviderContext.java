package com.neweraorganizations.webhook.core.provider;

public class ProviderContext {

    private final Long providerId;
    private final String providerKey;
    private final String status;

    public ProviderContext(Long providerId, String providerKey, String status) {
        this.providerId = providerId;
        this.providerKey = providerKey;
        this.status = status;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public String getStatus() {
        return status;
    }
}
