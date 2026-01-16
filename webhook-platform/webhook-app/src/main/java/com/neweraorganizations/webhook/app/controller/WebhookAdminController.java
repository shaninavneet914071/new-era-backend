package com.neweraorganizations.webhook.app.controller;

import com.neweraorganizations.webhook.app.service.WebhookReplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/webhooks")
public class WebhookAdminController {

    private static final Logger log =
            LoggerFactory.getLogger(WebhookAdminController.class);

    private final WebhookReplayService webhookReplayService;

    public WebhookAdminController(WebhookReplayService webhookReplayService) {
        this.webhookReplayService = webhookReplayService;
    }

    /**
     * Replay all webhook events that are not SUCCESS.
     */
    @PostMapping("/replay")
    public ResponseEntity<String> replayFailedWebhooks() {

        int replayedCount = webhookReplayService.replayFailedEvents();

        log.info("Webhook replay completed | replayedCount={}", replayedCount);

        return ResponseEntity.ok(
                "Replayed " + replayedCount + " webhook events"
        );
    }
}
