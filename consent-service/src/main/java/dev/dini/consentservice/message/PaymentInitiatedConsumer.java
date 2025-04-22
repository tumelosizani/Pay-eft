package dev.dini.consentservice.message;

import dev.dini.consentservice.dto.ConsentRequestDTO;
import dev.dini.consentservice.dto.PaymentInitiatedEvent;
import dev.dini.consentservice.entity.ConsentStatus;
import dev.dini.consentservice.entity.ConsentType;
import dev.dini.consentservice.service.ConsentService; // To use createConsent
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentInitiatedConsumer {

    private final ConsentService consentService;

    @Transactional
    @KafkaListener(topics = "payment-initiated", groupId = "consent-service-group")
    public void consumePaymentInitiatedEvent(PaymentInitiatedEvent event) {
        log.info("Received payment initiated event: {}", event);

        ConsentRequestDTO consentRequestDTO = new ConsentRequestDTO();
        consentRequestDTO.setUserId(event.customerId());
        consentRequestDTO.setPaymentRequestId(event.paymentRequestId());
        consentRequestDTO.setType(ConsentType.PUSH_NOTIFICATION);
        consentRequestDTO.setStatus(ConsentStatus.PENDING);


        // Use the ConsentService to create the Consent entity
        consentService.createConsent(consentRequestDTO);

        log.info("Created pending consent for payment request: {}", event.paymentRequestId());
    }
}