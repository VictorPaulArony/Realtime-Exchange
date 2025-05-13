package com.main_service.service;

import com.main_service.dto.ConversionRequest;
import com.main_service.dto.ConversionResponse;
import com.main_service.model.Conversion;
import com.main_service.repository.ConversionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

// ConversionServiceTest class to test the ConversionService
class ConversionServiceTest {

    @Mock
    private ConversionRepository conversionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ConversionService conversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertCurrency_Success() {
        // Arrange
        ConversionRequest request = new ConversionRequest();
        request.setFrom("USD");
        request.setTo("EUR");
        request.setAmount(new BigDecimal("100.00"));

        Map<String, Object> rateResponse = Map.of("rate", "0.85");
        when(restTemplate.getForObject(anyString(), eq(Map.class)))
            .thenReturn(rateResponse);

        when(conversionRepository.save(any(Conversion.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ConversionResponse response = conversionService.convertCurrency(request);

        // Assert
        assertNotNull(response);
        assertEquals("USD", response.getFrom());
        assertEquals("EUR", response.getTo());
        assertEquals(new BigDecimal("100.00"), response.getAmount());
        assertEquals(new BigDecimal("0.85"), response.getRate());
        assertEquals(new BigDecimal("85.00"), response.getResult());

        verify(restTemplate).getForObject(anyString(), eq(Map.class));
        verify(conversionRepository).save(any(Conversion.class));
    }

    @Test
    void convertCurrency_RateServiceFailure() {
        // Arrange
        ConversionRequest request = new ConversionRequest();
        request.setFrom("USD");
        request.setTo("EUR");
        request.setAmount(new BigDecimal("100.00"));

        when(restTemplate.getForObject(anyString(), eq(Map.class)))
            .thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            conversionService.convertCurrency(request));

        verify(restTemplate).getForObject(anyString(), eq(Map.class));
        verify(conversionRepository, never()).save(any(Conversion.class));
    }
}
