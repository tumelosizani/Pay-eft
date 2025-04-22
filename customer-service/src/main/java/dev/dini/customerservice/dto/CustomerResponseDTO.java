package dev.dini.customerservice.dto;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String idNumber,
        String address
) {
}
