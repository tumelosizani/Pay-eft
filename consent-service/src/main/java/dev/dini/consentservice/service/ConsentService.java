package dev.dini.consentservice.service;

import dev.dini.consentservice.client.TwoFaCallbackDTO;
import dev.dini.consentservice.dto.ConsentRequestDTO;
import dev.dini.consentservice.dto.ConsentResponseDTO;
import dev.dini.consentservice.dto.ConsentStatusResponseDTO;
import dev.dini.consentservice.entity.ConsentStatus;

import java.util.UUID;

public interface ConsentService {
    ConsentResponseDTO createConsent(ConsentRequestDTO request);
    ConsentResponseDTO getConsentByPaymentRequestId(UUID paymentRequestId);
    void updateConsentStatus(UUID consentId, ConsentStatus newStatus);
    void handleTwoFaCallback(TwoFaCallbackDTO callback);
    ConsentStatusResponseDTO getConsentStatusByPaymentRequestId(UUID paymentRequestId);

    ConsentResponseDTO getConsentById(UUID consentId);
}
