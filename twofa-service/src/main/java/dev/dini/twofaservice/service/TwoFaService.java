package dev.dini.twofaservice.service;

import dev.dini.twofaservice.dto.TwoFaCreateDTO;
import dev.dini.twofaservice.dto.TwoFaResponseDTO;

import java.util.UUID;

public interface TwoFaService {
    TwoFaResponseDTO createTwoFaRequest(TwoFaCreateDTO dto);
    TwoFaResponseDTO approveRequest(UUID twoFaId);
    TwoFaResponseDTO rejectRequest(UUID twoFaId);
}
