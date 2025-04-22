package dev.dini.consentservice.repository;

import dev.dini.consentservice.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {
    Optional<Consent> findByPaymentRequestId(UUID paymentRequestId);
}
