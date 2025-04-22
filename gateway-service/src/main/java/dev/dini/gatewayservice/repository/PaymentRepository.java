package dev.dini.gatewayservice.repository;

import dev.dini.gatewayservice.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentRequest, UUID> {
}
