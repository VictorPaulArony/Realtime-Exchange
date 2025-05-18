package com.rate_service.controller;

import com.rate_service.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// RateControllerTest class to test the RateController
@WebMvcTest(RateController.class)
class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    @WithMockUser(username = "apiuser", roles = "API_USER")
    void getRate_ValidRequest_ReturnsRate() throws Exception {
        // Arrange
        when(exchangeRateService.getExchangeRate("USD", "EUR")).thenReturn(0.85);

        // Act & Assert
        mockMvc.perform(get("/rate")
                .param("from", "USD")
                .param("to", "EUR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(0.85))
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("EUR"));
    }

    // getRate_Unauthorized_Returns401 method to test the unauthorized response
    @Test
    void getRate_Unauthorized_Returns401() throws Exception {
        mockMvc.perform(get("/rate")
                .param("from", "USD")
                .param("to", "EUR"))
                .andExpect(status().isUnauthorized());
    }

    // getRate_InvalidCurrency_ReturnsBadRequest method to test the invalid currency response
    @Test
    @WithMockUser(username = "apiuser", roles = "API_USER")
    void getRate_InvalidCurrency_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/rate")
                .param("from", "INVALID")
                .param("to", "EUR"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "apiuser", roles = "API_USER")
    void getRate_MissingParameter_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/rate")
                .param("from", "USD"))
                .andExpect(status().isBadRequest());
    }
}
