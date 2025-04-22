package dev.dini.twofaservice.message;

import dev.dini.twofaservice.dto.TwoFaResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TwoFaResultProducer {

    private final KafkaTemplate<String, TwoFaResultEvent> kafkaTemplate;

    @Value("${kafka.topic.twofa-approved}")
    private String twoFaApprovedTopic;

    @Value("${kafka.topic.twofa-rejected}")
    private String twoFaRejectedTopic;

    public void publishTwoFaResultEvent(TwoFaResultEvent event) {
        if (event.status() == dev.dini.twofaservice.entity.TwoFaStatus.APPROVED) {
            kafkaTemplate.send(twoFaApprovedTopic, event);
            log.info("Published 2FA Approved event: {}", event);
        } else if (event.status() == dev.dini.twofaservice.entity.TwoFaStatus.REJECTED) {
            kafkaTemplate.send(twoFaRejectedTopic, event);
            log.info("Published 2FA Rejected event: {}", event);
        } else {
            log.warn("Attempted to publish TwoFaResultEvent with status: {}", event.status());
        }
    }
}