package com.neweraorganizations.webhook.app.controller;

import com.neweraorganizations.webhook.api.dto.WebhookEventAdminDto;
import com.neweraorganizations.webhook.app.service.WebhookReplayService;
import com.neweraorganizations.webhook.persistence.entity.WebhookEventEntity;
import com.neweraorganizations.webhook.persistence.repository.WebhookEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/webhooks")
public class WebhookAdminReadController {

    private final WebhookEventRepository webhookEventRepository;
    private final WebhookReplayService webhookReplayService;

    public WebhookAdminReadController(
            WebhookEventRepository webhookEventRepository, WebhookReplayService webhookReplayService
    ) {
        this.webhookEventRepository = webhookEventRepository;
        this.webhookReplayService = webhookReplayService;
    }

    @GetMapping
    public ResponseEntity<Page<WebhookEventAdminDto>> listWebhooks(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "provider", required = false) String provider,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sort", defaultValue = "receivedAt,desc") String sort
    ) {

        String[] sortParts = sort.split(",");
        Sort.Direction direction =
                sortParts.length > 1 && sortParts[1].equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortParts[0])
        );

        Page<WebhookEventEntity> result;

        if (status != null && provider != null) {
            result = webhookEventRepository
                    .findByStatusAndProvider(status, provider, pageable);

        } else if (status != null) {
            result = webhookEventRepository
                    .findByStatus(status, pageable);

        } else if (provider != null) {
            result = webhookEventRepository
                    .findByProvider(provider, pageable);

        } else {
            result = webhookEventRepository.findAll(pageable);
        }

        Page<WebhookEventAdminDto> dtoPage =
                result.map(event ->
                        new WebhookEventAdminDto(
                                event.getEventId(),
                                event.getProvider(),
                                event.getEventType(),
                                event.getStatus(),
                                event.getReceivedAt()
                        )
                );

        return ResponseEntity.ok(dtoPage);

    }

    @PostMapping("/dead-letter/{eventId}/replay")
    public ResponseEntity<String> replayDeadLetter(
            @PathVariable String eventId
    ) {
        webhookReplayService.replayDeadLetterEvent(eventId);
        return ResponseEntity.ok("Dead-letter event replay initiated");
    }
}
