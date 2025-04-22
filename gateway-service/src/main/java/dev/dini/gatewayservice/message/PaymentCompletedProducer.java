package dev.dini.gatewayservice.message;

import dev.dini.gatewayservice.dto.PaymentCompletedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedProducer {

    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    @Value("${kafka.topic.payment-completed}")
    private String paymentCompletedTopic;

    public PaymentCompletedProducer(KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentCompletedEvent(PaymentCompletedEvent event) {
        kafkaTemplate.send(paymentCompletedTopic, event);
    }

}
