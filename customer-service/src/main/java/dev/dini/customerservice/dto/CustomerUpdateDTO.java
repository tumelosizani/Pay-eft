package dev.dini.customerservice.dto;

public record CustomerUpdateDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String idNumber,
        String address
) {
}
