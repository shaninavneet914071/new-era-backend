package com.neweraorganizations.webhook.app.controller;

import com.neweraorganizations.webhook.app.ratelimit.WebhookRateLimiter;
import com.neweraorganizations.webhook.app.security.ProviderSecretResolver;
import com.neweraorganizations.webhook.app.service.WebhookAsyncProcessor;
import com.neweraorganizations.webhook.app.service.WebhookIngestionService;
import com.neweraorganizations.webhook.core.idempotency.WebhookIdempotencyService;
import com.neweraorganizations.webhook.core.router.WebhookRouter;
import com.neweraorganizations.webhook.core.security.WebhookSignatureVerifier;
import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private static final Logger log =
            LoggerFactory.getLogger(WebhookController.class);

    private final WebhookRouter webhookRouter;
    private final WebhookIdempotencyService idempotencyService;
    private final ProviderSecretResolver providerSecretResolver;
    private final WebhookIngestionService webhookIngestionService;

    private final WebhookAsyncProcessor webhookAsyncProcessor;

    private final WebhookRateLimiter webhookRateLimiter;

    public WebhookController(
            WebhookRouter webhookRouter,
            WebhookIdempotencyService idempotencyService,
            ProviderSecretResolver providerSecretResolver,
            WebhookIngestionService webhookIngestionService, WebhookAsyncProcessor webhookAsyncProcessor, WebhookRateLimiter webhookRateLimiter
    ) {
        this.webhookRouter = webhookRouter;
        this.idempotencyService = idempotencyService;
        this.providerSecretResolver = providerSecretResolver;
        this.webhookIngestionService = webhookIngestionService;
        this.webhookAsyncProcessor = webhookAsyncProcessor;
        this.webhookRateLimiter = webhookRateLimiter;
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveWebhook(
            @RequestHeader("X-Provider") String provider,
            HttpServletRequest request,
            @RequestHeader("X-Signature") String signature,
            @RequestHeader("X-Event-Id") String eventId,
            @RequestHeader(value = "X-Event-Type", required = false) String eventType,
            @RequestBody String payload
    ) {
        String clientIp = request.getRemoteAddr();

        if (!webhookRateLimiter.allow(provider, clientIp)) {
            log.warn("Rate limit exceeded | provider={} | ip={}", provider, clientIp);
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded");
        }

        String normalizedPayload = payload.trim();
        String secret = providerSecretResolver.resolveSecret(provider);
        // 1️⃣ Verify signature
        boolean valid = WebhookSignatureVerifier.verify(
                normalizedPayload,
                secret,
                signature
        );

        if (!valid) {
            webhookIngestionService.saveInitialEvent(
                    provider,
                    eventId,
                    eventType,
                    payload,
                    "INVALID_SIGNATURE"
            );

            log.warn("Rejected webhook due to invalid signature | eventId={}", eventId);

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid webhook signature");
        }

        // 2️⃣ Idempotency check
        if (idempotencyService.isDuplicate(eventId)) {
            log.warn("Duplicate webhook received | eventId={}", eventId);

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Duplicate webhook event");
        }

        // 3️⃣ Persist initial state
        WebhookEventEntity event = webhookIngestionService.saveInitialEvent(
                provider,
                eventId,
                eventType,
                payload,
                "PENDING"
        );
        // 4️⃣ Trigger async processing ONLY
        webhookAsyncProcessor.processAsync(event.getEventId());

        return ResponseEntity.ok("Webhook accepted");
    }
}
