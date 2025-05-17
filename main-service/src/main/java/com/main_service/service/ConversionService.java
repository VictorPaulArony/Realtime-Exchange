package com.main_service.service;

import com.main_service.dto.ConversionRequest;
import com.main_service.dto.ConversionResponse;
import com.main_service.model.Conversion;
import com.main_service.repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ConversionService {
    @Autowired
    private ConversionRepository conversionRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${rate-service.url}")
    private String rateServiceUrl;

    public ConversionResponse convertCurrency(ConversionRequest request) {
        // Get exchange rate from rate-service
        String rateUrl = String.format("%s/rate?from=%s&to=%s", 
            rateServiceUrl, request.getFrom(), request.getTo());
        @SuppressWarnings("unchecked")
        Map<String, Object> rateResponse = restTemplate.getForObject(
            rateUrl, Map.class);

        if (rateResponse == null || !rateResponse.containsKey("rate")) {
            throw new RuntimeException("Failed to get exchange rate");
        }

        BigDecimal rate = new BigDecimal(rateResponse.get("rate").toString());
        BigDecimal result = request.getAmount().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);

        // Save conversion details
        Conversion conversion = Conversion.builder()
            .fromCurrency(request.getFrom())
            .toCurrency(request.getTo())
            .amount(request.getAmount())
            .rate(rate)
            .result(result)
            .timestamp(LocalDateTime.now())
            .build();
        conversionRepository.save(conversion);

        // Return response
        return ConversionResponse.builder()
            .from(request.getFrom())
            .to(request.getTo())
            .amount(request.getAmount())
            .rate(rate)
            .result(result)
            .build();
    }
}
