package com.neweraorganizations.webhook.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "providers")
public class ProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_key", unique = true, nullable = false)
    private String providerKey;

    private String name;
    private String status;

    private Instant createdAt = Instant.now();

    public Long getId() {
        return id;
    }


    public String getProviderKey() {
        return providerKey;
    }

    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    // getters / setters
}
