package dev.dini.gatewayservice.message;

import dev.dini.gatewayservice.dto.PaymentInitiatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentInitiatedProducer {

    private final KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate;

    public PaymentInitiatedProducer(KafkaTemplate<String, PaymentInitiatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentInitiatedEvent(PaymentInitiatedEvent event) {
        kafkaTemplate.send("payment-initiated", event);
    }
}
