package dev.dini.twofaservice.controller;

import dev.dini.twofaservice.dto.TwoFaCreateDTO;
import dev.dini.twofaservice.dto.TwoFaResponseDTO;
import dev.dini.twofaservice.service.TwoFaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/2fa")
@RequiredArgsConstructor
public class TwoFAController {

    private final TwoFaService twoFaService;

    @PostMapping("/request")
    public ResponseEntity<TwoFaResponseDTO> requestTwoFa(
            @Valid @RequestBody TwoFaCreateDTO createDTO) {
        log.info("Requesting two fa: {}", createDTO);
        TwoFaResponseDTO response = twoFaService.createTwoFaRequest(createDTO);
        log.info("Two fa request created: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{twoFaId}/approve")
    public ResponseEntity<TwoFaResponseDTO> approveTwoFa(@PathVariable UUID twoFaId) {
        log.info("Approving two fa request with id: {}", twoFaId);
        TwoFaResponseDTO response = twoFaService.approveRequest(twoFaId);
        log.info("Two fa request approved: {}", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{twoFaId}/reject")
    public ResponseEntity<TwoFaResponseDTO> rejectTwoFa(@PathVariable UUID twoFaId) {
        log.info("Rejecting two fa request with id: {}", twoFaId);
        TwoFaResponseDTO response = twoFaService.rejectRequest(twoFaId);
        log.info("Two fa request rejected: {}", response);
        return ResponseEntity.ok(response);
    }

}