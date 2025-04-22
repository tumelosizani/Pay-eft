package dev.dini.twofaservice.repository;

import dev.dini.twofaservice.entity.TwoFaRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TwoFaRequestRepository extends JpaRepository<TwoFaRequest, UUID> {
}
