package com.rate_service.service;

import com.rate_service.exception.InvalidCurrencyException;
import com.rate_service.model.ConversionRates;
import com.rate_service.model.ExchangeRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

// ExchangeRateServiceTest class to test the ExchangeRateService
@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExchangeRateService(webClient);
    }

    // getExchangeRate_ValidCurrencies_ReturnsRate method to test the valid currency response
    @Test
    void getExchangeRate_ValidCurrencies_ReturnsRate() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        ConversionRates rates = new ConversionRates();
        rates.setRate("EUR", 0.85);
        response.setConversionRates(rates);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(Mono.just(response));

        // Act
        Double rate = exchangeRateService.getExchangeRate("USD", "EUR");

        // Assert
        assertEquals(0.85, rate);
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri("/latest/USD");
    }

    // getExchangeRate_InvalidTargetCurrency_ThrowsException method to test the invalid currency response
    @Test
    void getExchangeRate_InvalidTargetCurrency_ThrowsException() {
        // Arrange
        ExchangeRateResponse response = new ExchangeRateResponse();
        response.setConversionRates(new ConversionRates());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(Mono.just(response));

        // Act & Assert
        assertThrows(InvalidCurrencyException.class, () -> 
            exchangeRateService.getExchangeRate("USD", "INVALID")
        );
    }
}
