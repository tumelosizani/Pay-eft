package dev.dini.customerservice.service;

import dev.dini.customerservice.dto.CustomerResponseDTO;

import java.util.Optional;
import java.util.UUID;

public interface CustomerReadService {
    Optional<CustomerResponseDTO> findCustomerByIdentifier(String identifier);

    Optional<CustomerResponseDTO> getCustomerById(UUID customerId);
}