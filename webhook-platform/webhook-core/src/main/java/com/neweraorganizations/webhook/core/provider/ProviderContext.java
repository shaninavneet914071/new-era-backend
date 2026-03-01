package com.neweraorganizations.webhook.core.provider;

public class ProviderContext {

    private final Long providerId;
    private final String providerKey;
    private final boolean isActive;

    public ProviderContext(Long providerId, String providerKey, boolean status) {
        this.providerId = providerId;
        this.providerKey = providerKey;
        this.isActive = status;
    }

    public Long getProviderId() {
        return providerId;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
