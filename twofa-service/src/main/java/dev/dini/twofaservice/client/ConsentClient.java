package dev.dini.twofaservice.client;

import dev.dini.twofaservice.config.FeignClientConfig;
import dev.dini.twofaservice.dto.TwoFaCallbackDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "consentClient", url = "${services.consent.url}", configuration = FeignClientConfig.class)
public interface ConsentClient {


    @PatchMapping("/consents/2fa-callback")
    void handle2FaCallback(@RequestBody TwoFaCallbackDTO callbackDTO);
}
