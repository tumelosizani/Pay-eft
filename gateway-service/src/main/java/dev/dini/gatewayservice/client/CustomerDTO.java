package dev.dini.gatewayservice.client;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String idNumber,
        String address

        ) {
}
