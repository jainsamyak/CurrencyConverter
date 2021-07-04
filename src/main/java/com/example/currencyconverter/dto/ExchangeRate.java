package com.example.currencyconverter.dto;

import com.example.currencyconverter.exception.ExchangeRateNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExchangeRate {
    private String base;
    private Long timestamp;
    private String date;
    private Map<String, Double> rates;

    public Double getCurrencyRate(String currencyCode){

        if(!this.getRates().containsKey(currencyCode)){
            throw new ExchangeRateNotFoundException(currencyCode);
        }

        return this.getRates().get(currencyCode);
    }
}
