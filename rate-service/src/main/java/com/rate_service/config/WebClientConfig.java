package com.rate_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

// WebClientConfig class to configure the WebClient bean
@Configuration
public class WebClientConfig {

    @Value("${exchangerate.api.baseurl:https://v6.exchangerate-api.com/v6}")
    private String baseUrl;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Bean
    public WebClient exchangeRateWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl + "/" + apiKey)
                .build();
    }
}
