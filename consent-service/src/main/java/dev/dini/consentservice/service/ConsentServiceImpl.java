package dev.dini.consentservice.service;

import dev.dini.consentservice.client.*;
import dev.dini.consentservice.dto.*;
import dev.dini.consentservice.entity.Consent;
import dev.dini.consentservice.entity.ConsentStatus;
import dev.dini.consentservice.exception.ConsentNotFoundException;
import dev.dini.consentservice.mapper.ConsentMapper;
import dev.dini.consentservice.repository.ConsentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsentServiceImpl implements ConsentService {

    private final ConsentRepository consentRepository;
    private final ConsentMapper consentMapper;
    private final TwoFaClient twoFaClient;

    @Override
    public ConsentResponseDTO createConsent(@Valid ConsentRequestDTO request) {
        log.info("Create consent request: {}", request);
        Consent consent = consentMapper.toEntity(request);
        consent.setStatus(ConsentStatus.PENDING);
        Consent savedConsent = consentRepository.save(consent);
        log.info("Saved consent: {}", savedConsent);

        log.info("Saved Consent Status: {}", savedConsent.getStatus());
        TwoFaRequestDTO twoFaRequest = new TwoFaRequestDTO(
                request.getUserId(),
                request.getPaymentRequestId(),
                savedConsent.getId()
        );

        try {
            log.info("Sending two fa request");
            twoFaClient.send2FaRequest(twoFaRequest);
            log.info("Two fa request sent successfully");

        } catch (Exception e) {
            System.err.println("Error calling 2FA Service: " + e.getMessage());
            log.error("Error calling 2FA Service: {}", e.getMessage());
        }

        return consentMapper.toResponseDTO(savedConsent);
    }

    @Override
    public ConsentResponseDTO getConsentByPaymentRequestId(UUID paymentRequestId) {
        Consent consent = consentRepository.findByPaymentRequestId(paymentRequestId)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found for payment request ID: " + paymentRequestId));
        return consentMapper.toResponseDTO(consent);
    }

    @Override
    public void updateConsentStatus(UUID consentId, ConsentStatus newStatus) {
        Consent consent = consentRepository.findById(consentId)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found for ID: " + consentId));
        updateConsentStatusInternal(consent, newStatus);
    }


    @Override
    @Transactional
    public void handleTwoFaCallback(TwoFaCallbackDTO callback) {
        UUID consentId = callback.getConsentId();
        Consent consent = consentRepository.findById(consentId)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found for ID: " + consentId));

        if (callback.getStatus() == TwoFaStatus.APPROVED) {
            updateConsentStatusInternal(consent, ConsentStatus.APPROVED);
        } else if (callback.getStatus() == TwoFaStatus.REJECTED) {
            updateConsentStatusInternal(consent, ConsentStatus.REJECTED);
        }
        consentRepository.save(consent);
        log.info("Consent status updated based on 2FA callback: {}", consent);
    }

    private void updateConsentStatusInternal(Consent consent, ConsentStatus newStatus) {
        consent.setStatus(newStatus);
        consentRepository.save(consent);
        log.info("Updated consent status to {} for ID: {}", newStatus, consent.getId());
    }

    @Override
    public ConsentStatusResponseDTO getConsentStatusByPaymentRequestId(UUID paymentRequestId) {
        Consent consent = consentRepository.findByPaymentRequestId(paymentRequestId)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found for payment"));

        return new ConsentStatusResponseDTO(consent.getStatus());
    }

    @Override
    public ConsentResponseDTO getConsentById(UUID consentId) {
        Consent consent = consentRepository.findById(consentId)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found for ID: " + consentId));
        return consentMapper.toResponseDTO(consent);
    }

}
