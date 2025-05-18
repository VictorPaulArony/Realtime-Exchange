package com.main_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.Collections;

@Configuration
public class AppConfig {
    @Value("${rate-service.username}")
    private String rateServiceUsername;

    @Value("${rate-service.password}")
    private String rateServicePassword;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        
        // Add Basic Auth interceptor
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            String auth = rateServiceUsername + ":" + rateServicePassword;
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String(encodedAuth);
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, authHeader);
            return execution.execute(request, body);
        };
        
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }
}
