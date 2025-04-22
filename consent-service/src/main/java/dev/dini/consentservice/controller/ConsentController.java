package dev.dini.consentservice.controller;

import dev.dini.consentservice.client.TwoFaCallbackDTO;
import dev.dini.consentservice.dto.ConsentRequestDTO;
import dev.dini.consentservice.dto.ConsentResponseDTO;
import dev.dini.consentservice.dto.ConsentStatusResponseDTO;
import dev.dini.consentservice.entity.ConsentStatus;
import dev.dini.consentservice.service.ConsentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/consents")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentService consentService;


    // 1️⃣ Create Consent (Payment Gateway triggers this)
    @PostMapping
    public ResponseEntity<ConsentResponseDTO> createConsent(@Valid @RequestBody ConsentRequestDTO request) {
        ConsentResponseDTO response = consentService.createConsent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2️⃣ 2FA Callback (2FA service calls this when user approves/denies)
    @PatchMapping("/2fa-callback")
    public ResponseEntity<Void> handle2FaCallback(@RequestBody TwoFaCallbackDTO callback) {
        log.info("Received two fa callback: {}", callback);
        consentService.handleTwoFaCallback(callback);
        return ResponseEntity.ok().build();
    }

    // 3️⃣ Get Consent Status (Payment Gateway polls this to check result)
    @GetMapping("/status")
    public ResponseEntity<ConsentStatusResponseDTO> getConsentStatus(
            @RequestParam UUID paymentRequestId) {
        return ResponseEntity.ok(consentService.getConsentStatusByPaymentRequestId(paymentRequestId));
    }

    @PatchMapping("/{consentId}/status")
    public ResponseEntity<Void> updateConsentStatus(
            @PathVariable UUID consentId,
            @RequestParam ConsentStatus newStatus) {
        consentService.updateConsentStatus(consentId, newStatus);
        return ResponseEntity.noContent().build();
    }
}
