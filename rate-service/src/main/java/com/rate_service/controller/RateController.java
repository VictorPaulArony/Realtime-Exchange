package com.rate_service.controller;

import com.rate_service.service.ExchangeRateService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// RateController class to handle the rate endpoint
@RestController
@Validated
public class RateController {

    private final ExchangeRateService exchangeRateService;

    public RateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    // getRate method to handle the rate endpoint
    @GetMapping("/rate")
    public ResponseEntity<Map<String, Object>> getRate(
            @RequestParam @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid 'from' currency code") String from,
            @RequestParam @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid 'to' currency code") String to) {
        
        Double rate = exchangeRateService.getExchangeRate(from, to);
        return ResponseEntity.ok(Map.of(
            "from", from,
            "to", to,
            "rate", rate
        ));
    }
}
