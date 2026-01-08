package com.neweraorganizations.webhook.app.controller;

import com.neweraorganizations.webhook.core.idempotency.WebhookIdempotencyService;
import com.neweraorganizations.webhook.core.router.WebhookRouter;
import com.neweraorganizations.webhook.core.security.WebhookSignatureVerifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    private final WebhookRouter webhookRouter;
    private final WebhookIdempotencyService idempotencyService;

    // TEMP: hardcoded secret (will externalize)
    private static final String WEBHOOK_SECRET = "my-secret-key";

    public WebhookController(
            WebhookRouter webhookRouter,
            WebhookIdempotencyService idempotencyService
    ) {
        this.webhookRouter = webhookRouter;
        this.idempotencyService = idempotencyService;
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveWebhook(
            @RequestHeader("X-Provider") String provider,
            @RequestHeader("X-Signature") String signature,
            @RequestHeader("X-Event-Id") String eventId,
            @RequestHeader(value = "X-Event-Type", required = false) String eventType,
            @RequestBody String payload) {

        // 1️⃣ Signature verification
        boolean valid = WebhookSignatureVerifier.verify(
                payload,
                WEBHOOK_SECRET,
                signature
        );

        if (!valid) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid webhook signature");
        }

        // 2️⃣ Idempotency check
        if (idempotencyService.isDuplicate(eventId)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Duplicate webhook event");
        }

        // 3️⃣ Process webhook
        webhookRouter.route(provider, eventType, payload);

        // 4️⃣ Mark processed
        idempotencyService.markProcessed(eventId);

        return ResponseEntity.ok("Webhook processed");
    }
}
