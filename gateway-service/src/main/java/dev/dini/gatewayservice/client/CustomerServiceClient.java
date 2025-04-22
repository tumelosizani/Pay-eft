package dev.dini.gatewayservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerServiceClient {

    @GetMapping("/verify")
    CustomerDTO verifyCustomer(@RequestParam("identifier") String identifier);
}

