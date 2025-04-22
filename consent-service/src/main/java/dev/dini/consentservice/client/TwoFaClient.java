package dev.dini.consentservice.client;

import dev.dini.consentservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "twoFaClient", url = "${services.2fa.url}", configuration = FeignClientConfig.class)
public interface TwoFaClient {

    @PostMapping("/2fa/request")
    void send2FaRequest(@RequestBody TwoFaRequestDTO requestDTO);

    @PatchMapping("/2fa/{twoFaId}/approve")
    void approve2Fa(@PathVariable UUID twoFaId, @RequestParam TwoFaCallbackDTO callbackDTO);

    @PatchMapping("/2fa/{twoFaId}/reject")
    void reject2Fa(@PathVariable UUID twoFaId, @RequestParam TwoFaCallbackDTO callbackDTO);
}
