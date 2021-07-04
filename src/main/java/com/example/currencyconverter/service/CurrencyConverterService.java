package com.example.currencyconverter.service;

import com.example.currencyconverter.dto.CurrencyExchange;
import com.example.currencyconverter.dto.ExchangeRate;
import com.example.currencyconverter.exception.ExchangeRateNotFoundException;
import com.example.currencyconverter.utils.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService {

    ExchangeRatesService exchangeRatesService;
    CurrencyFormatService currencyFormatService;

    @Autowired
    public CurrencyConverterService(ExchangeRatesService exchangeRatesService, CurrencyFormatService currencyFormatService){
        this.exchangeRatesService = exchangeRatesService;
        this.currencyFormatService = currencyFormatService;
    }

    public CurrencyExchange convertCurrency(String sourceCurrency, String targetCurrency, Double currencyAmount){
        try {
            sourceCurrency = Utilities.convertToUpperCase(sourceCurrency);
            targetCurrency = Utilities.convertToUpperCase(targetCurrency);

            ExchangeRate exchangeRate = this.exchangeRatesService.getExchangeRates();
            Double convertedBaseCurrency = this.exchangeRatesService.convertAmountFromTargetToBase(currencyAmount, sourceCurrency, exchangeRate);
            Double convertedTargetCurrency = this.exchangeRatesService.convertAmountFromBaseToTarget(convertedBaseCurrency, targetCurrency, exchangeRate);
            String formattedAmount = this.currencyFormatService.formatCurrency(targetCurrency, convertedTargetCurrency);
            Double rate = Utilities.roundNumber(convertedTargetCurrency / currencyAmount, 5);

            return new CurrencyExchange(sourceCurrency, targetCurrency, rate, Utilities.roundNumber(convertedTargetCurrency, 5), formattedAmount);
        }
        catch (ExchangeRateNotFoundException e){
            throw e;
        }
        catch (Error | Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
