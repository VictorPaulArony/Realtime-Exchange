package com.rate_service.service;

import com.rate_service.exception.InvalidCurrencyException;
import com.rate_service.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

// ExchangeRateService class to handle the exchange rate service
@Service
public class ExchangeRateService {

    private final WebClient exchangeRateWebClient;

    public ExchangeRateService(WebClient exchangeRateWebClient) {
        this.exchangeRateWebClient = exchangeRateWebClient;
    }

    // getExchangeRate method to get the exchange rate for a specific currency
    public Double getExchangeRate(String from, String to) {
        return exchangeRateWebClient.get()
                .uri("/latest/" + from)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .map(response -> {
                    Double rate = response.getConversionRates().getRate(to);
                    if (rate == null) {
                        throw new InvalidCurrencyException("Invalid currency code: " + to);
                    }
                    return rate;
                })
                .block();
    }
}
