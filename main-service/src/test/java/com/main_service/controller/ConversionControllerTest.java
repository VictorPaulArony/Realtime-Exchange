package com.main_service.controller;

import com.main_service.dto.ConversionRequest;
import com.main_service.dto.ConversionResponse;
import com.main_service.service.ConversionService;
import com.main_service.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// ConversionControllerTest class to test the ConversionController
@WebMvcTest(ConversionController.class)
@Import(TestSecurityConfig.class)
class ConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversionService conversionService;

    @Test
    void getStatus_ShouldReturnUp() throws Exception {
        mockMvc.perform(get("/status")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"status\":\"UP\"}"));
    }

    @Test
    @WithMockUser(username = "apiuser", password = "apipass", roles = "API")
    void convertCurrency_Success() throws Exception {
        // Arrange
        ConversionResponse response = ConversionResponse.builder()
            .from("USD")
            .to("EUR")
            .amount(new BigDecimal("100.00"))
            .rate(new BigDecimal("0.85"))
            .result(new BigDecimal("85.00"))
            .build();

        when(conversionService.convertCurrency(any(ConversionRequest.class)))
            .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":100.00}")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.from").value("USD"))
            .andExpect(jsonPath("$.to").value("EUR"))
            .andExpect(jsonPath("$.amount").value("100.00"))
            .andExpect(jsonPath("$.rate").value("0.85"))
            .andExpect(jsonPath("$.result").value("85.00"));
    }

    @Test
    void convertCurrency_Unauthorized() throws Exception {
        mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":100.00}")
                .with(csrf()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "apiuser", roles = "API")
    void convertCurrency_ValidationError() throws Exception {
        mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":-100.00}")
                .with(csrf()))
            .andExpect(status().isBadRequest());
    }
}
