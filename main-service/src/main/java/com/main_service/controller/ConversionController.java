package com.main_service.controller;

import com.main_service.dto.ConversionRequest;
import com.main_service.dto.ConversionResponse;
import com.main_service.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// ConversionController class to handle conversion requests
@RestController
public class ConversionController {
    @Autowired
    private ConversionService conversionService;

    // endpoint to get the service status
    @GetMapping("/status") 
    public ResponseEntity<Map<String, String>> getStatus() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    //endpoint to convert currency
    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convertCurrency(
            @Valid @RequestBody ConversionRequest request) {
        return ResponseEntity.ok(conversionService.convertCurrency(request));
    }
}
