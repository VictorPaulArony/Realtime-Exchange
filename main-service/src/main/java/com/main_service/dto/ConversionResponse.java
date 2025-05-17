package com.main_service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionResponse {
    private String from;
    private String to;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal amount;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal rate;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal result;

    public ConversionResponse() {}

    public static ConversionResponse.Builder builder() {
        return new ConversionResponse.Builder();
    }

    public static class Builder {
        private final ConversionResponse response;

        public Builder() {
            response = new ConversionResponse();
        }

        public Builder from(String from) {
            response.from = from;
            return this;
        }

        public Builder to(String to) {
            response.to = to;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            response.amount = amount.setScale(2, RoundingMode.HALF_UP);
            return this;
        }

        public Builder rate(BigDecimal rate) {
            response.rate = rate.setScale(2, RoundingMode.HALF_UP);
            return this;
        }

        public Builder result(BigDecimal result) {
            response.result = result.setScale(2, RoundingMode.HALF_UP);
            return this;
        }

        public ConversionResponse build() {
            return response;
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }
}
