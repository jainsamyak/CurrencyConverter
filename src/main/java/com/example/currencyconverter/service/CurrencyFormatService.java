package com.example.currencyconverter.service;

import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Service
public class CurrencyFormatService {

    public String formatCurrency(String currencyCode, Double amount){
        Currency currency = Currency.getInstance(currencyCode);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        format.setCurrency(currency);
        return format.format(amount);
    }
}
