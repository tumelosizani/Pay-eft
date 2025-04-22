package dev.dini.twofaservice.service;

import dev.dini.twofaservice.client.*;
import dev.dini.twofaservice.dto.*;
import dev.dini.twofaservice.entity.TwoFaRequest;
import dev.dini.twofaservice.entity.TwoFaStatus;
import dev.dini.twofaservice.exception.TwoFaNotFoundException;
import dev.dini.twofaservice.mapper.TwoFaMapper;
import dev.dini.twofaservice.message.TwoFaResultProducer;
import dev.dini.twofaservice.repository.TwoFaRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwoFaServiceImpl implements TwoFaService {

    private final TwoFaRequestRepository repository;
    private final TwoFaMapper twoFaMapper;
    private final NotificationClient notificationClient;
    private final TwoFaResultProducer twoFaResultProducer;
    private final ConsentClient consentClient;

    @Override
    public TwoFaResponseDTO createTwoFaRequest(TwoFaCreateDTO createRequestDTO) {
        log.info("Create two fa request: {}", createRequestDTO);
        TwoFaRequest request = twoFaMapper.toEntity(createRequestDTO);
        request.setCustomerId(createRequestDTO.customerId());
        request.setPaymentRequestId(createRequestDTO.paymentRequestId());
        request.setStatus(TwoFaStatus.PENDING);
        log.info("Sending payment request: {}", request);

        repository.save(request);

        notificationClient.send2FaNotification(request.getCustomerId(), request.getPaymentRequestId());

        return twoFaMapper.toResponseDTO(request);
    }


    public TwoFaResponseDTO handleApproval(UUID twoFaId) {
        TwoFaRequest request = repository.findById(twoFaId)
                .orElseThrow(() -> new TwoFaNotFoundException("2FA Request not found"));
        request.setStatus(TwoFaStatus.APPROVED);
        repository.save(request);


        // Publish the 2FA approved event
        TwoFaResultEvent resultEvent = new TwoFaResultEvent(request.getPaymentRequestId(), request.getStatus());
        twoFaResultProducer.publishTwoFaResultEvent(resultEvent);
        log.info("2FA request approved event: {}", request);


        // Send callback to Consent Service using Feign Client
        sendConsentCallback(request, TwoFaStatus.APPROVED);
        log.info("Sent approval callback to Consent Service for consentId: {}", request.getConsentId());

        return twoFaMapper.toResponseDTO(request);
    }


    public TwoFaResponseDTO handleRejection(UUID twoFaId) {
        TwoFaRequest request = repository.findById(twoFaId)
                .orElseThrow(() -> new TwoFaNotFoundException("2FA Request not found"));

        request.setStatus(TwoFaStatus.REJECTED);

        // Publish the 2FA rejected event
        TwoFaResultEvent resultEvent = new TwoFaResultEvent(request.getPaymentRequestId(), request.getStatus());
        twoFaResultProducer.publishTwoFaResultEvent(resultEvent);

        // Send callback to Consent Service using Feign Client
        sendConsentCallback(request, TwoFaStatus.REJECTED);

        log.info("Sent rejected callback to Consent Service for consentId: {}", request.getConsentId());

        return twoFaMapper.toResponseDTO(request);
    }

    private void sendConsentCallback(TwoFaRequest request, TwoFaStatus status) {
        TwoFaCallbackDTO callbackDTO = new TwoFaCallbackDTO(
                request.getConsentId(),
                status
        );
        consentClient.handle2FaCallback(callbackDTO);
        log.info("Sent {} callback to Consent Service for consentId: {}", status, request.getConsentId());
    }

    @Override
    @Transactional
    public TwoFaResponseDTO approveRequest(UUID twoFaId) {
        return handleApproval(twoFaId);
    }

    @Override
    @Transactional
    public TwoFaResponseDTO rejectRequest(UUID twoFaId) {
        return handleRejection(twoFaId);
    }


}
