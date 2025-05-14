package com.rate_service.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

// ConversionRates class to represent the conversion rates
@Data
public class ConversionRates {
    private Map<String, Double> rates = new HashMap<>();

    // setRate method to set the conversion rate for a specific currency
    @JsonAnySetter
    public void setRate(String currency, Double rate) {
        rates.put(currency, rate);
    }

    // getRate method to get the conversion rate for a specific currency
    public Double getRate(String currency) {
        return rates.get(currency);
    }
}
