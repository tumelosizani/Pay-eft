package dev.dini.twofaservice.client;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationClient {
    public void send2FaNotification(UUID customerId, UUID requestId) {
        // Simulate push notification to mobile app
        System.out.printf("2FA request sent to customer [%s] for request [%s]%n", customerId, requestId);
    }
}
