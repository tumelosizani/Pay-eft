package dev.dini.gatewayservice.message;

import dev.dini.gatewayservice.dto.TwoFaResultEvent;
import dev.dini.gatewayservice.entity.PaymentStatus;
import dev.dini.gatewayservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TwoFaResultConsumer {

    private final PaymentService paymentService;

    @Transactional
    @KafkaListener(topics = "${kafka.topic.twofa-approved}", groupId = "gateway-service-twofa-result-group")
    public void consumeTwoFaApprovedEvent(TwoFaResultEvent event) {
        log.info("Received 2FA Approved event: {}", event);
        paymentService.updatePaymentStatus(event.paymentRequestId(), PaymentStatus.APPROVED);
        log.info("Payment status updated to APPROVED for request: {}", event.paymentRequestId());
        // 4. Trigger payment completed - kafka
        paymentService.completePayment(event.paymentRequestId());
        log.info("Payment completed for request: {}", event.paymentRequestId());
    }

    @Transactional
    @KafkaListener(topics = "${kafka.topic.twofa-rejected}", groupId = "gateway-service-twofa-result-group")
    public void consumeTwoFaRejectedEvent(TwoFaResultEvent event) {
        log.info("Received 2FA Rejected event: {}", event);
        paymentService.updatePaymentStatus(event.paymentRequestId(), PaymentStatus.FAILED); // Or another appropriate status
    }

}