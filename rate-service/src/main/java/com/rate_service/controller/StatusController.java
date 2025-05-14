package com.rate_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// StatusController class to handle the status endpoint
@RestController
public class StatusController {

    // getStatus method to handle the status endpoint
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getStatus() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
