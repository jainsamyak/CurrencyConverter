package com.example.currencyconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Exchange rate not found!")
public class ExchangeRateNotFoundException extends RuntimeException {
    public ExchangeRateNotFoundException(String currencyCode){
        super("Exchange Rate not found for currency: " + currencyCode);
    }
}
