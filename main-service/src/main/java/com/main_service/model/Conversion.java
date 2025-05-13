package com.main_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//conversion class represents a conversion between two currencies
@Table("conversions")
public class Conversion {
    @Id
    private Long id;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
    private BigDecimal rate;
    private BigDecimal result;
    private LocalDateTime timestamp;

    public Conversion() {}

    public static Builder builder() {
        return new Builder();
    }

    //conversion builder class
    public static class Builder {
        private final Conversion conversion;

        public Builder() {
            conversion = new Conversion();
        }

        public Builder id(Long id) {
            conversion.id = id;
            return this;
        }

        public Builder fromCurrency(String fromCurrency) {
            conversion.fromCurrency = fromCurrency;
            return this;
        }

        public Builder toCurrency(String toCurrency) {
            conversion.toCurrency = toCurrency;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            conversion.amount = amount;
            return this;
        }

        public Builder rate(BigDecimal rate) {
            conversion.rate = rate;
            return this;
        }

        public Builder result(BigDecimal result) {
            conversion.result = result;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            conversion.timestamp = timestamp;
            return this;
        }

        public Conversion build() {
            return conversion;
        }
    }

    //getters and setters for the fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
