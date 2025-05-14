package com.rate_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// ExchangeRateResponse class to represent the exchange rate response
@Data
public class ExchangeRateResponse {
    private String result;
    private String documentation;
    private String terms;
    @JsonProperty("time_last_update_utc")
    private String timeLastUpdateUtc;
    @JsonProperty("base_code")
    private String baseCode;
    @JsonProperty("conversion_rates")
    private ConversionRates conversionRates;

    public ConversionRates getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(ConversionRates conversionRates) {
        this.conversionRates = conversionRates;
    }
}
