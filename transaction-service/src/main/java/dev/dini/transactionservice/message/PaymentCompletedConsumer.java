package dev.dini.transactionservice.message;

import dev.dini.transactionservice.dto.PaymentCompletedEvent;
import dev.dini.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedConsumer {

    private final TransactionService transactionService;

    @KafkaListener(topics = "${kafka.topic.payment-completed}" ,groupId = "transaction-service-payment-completed-group")
    public void consumePaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("Received Payment Completed Event: {}", event);
        transactionService.processCompletedEftPayment(event);
    }
}
