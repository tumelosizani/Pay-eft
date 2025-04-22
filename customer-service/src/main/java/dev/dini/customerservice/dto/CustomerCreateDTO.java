package dev.dini.customerservice.dto;

public record CustomerCreateDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String idNumber,
        String address
) {
}