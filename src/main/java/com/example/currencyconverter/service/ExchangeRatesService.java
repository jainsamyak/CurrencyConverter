package com.example.currencyconverter.service;

import com.example.currencyconverter.dto.ExchangeRate;
import com.example.currencyconverter.exception.ExchangeRateNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class ExchangeRatesService {

    @Value("${EXCHANGE_RATES_API_KEY}")
    private String apiKey;

    @Value("${EXCHANGE_RATES_API_URL}")
    private String apiUrl;

    private final RPCService rpcService;

    @Autowired
    public ExchangeRatesService(RPCService rpcService){
        this.rpcService = rpcService;
    }

    @Cacheable(value = "exchangeRate", key = "'exchangeRate'")
    public ExchangeRate getExchangeRates(){
        try {
            final String url = apiUrl + "?access_key=" + apiKey + "&format=1";
            String response = rpcService.get(url);
            ObjectMapper objectMapper = new ObjectMapper();
            ExchangeRate exchangeRates = objectMapper.readValue(response, ExchangeRate.class);
            return exchangeRates;
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Double convertAmountFromTargetToBase(Double amount, String targetCurrency, ExchangeRate exchangeRates) throws ExchangeRateNotFoundException {
        Double convertedAmount;
        convertedAmount = amount / exchangeRates.getCurrencyRate(targetCurrency);
        return roundTo2DecimalPlaces(convertedAmount);
    }

    public Double convertAmountFromBaseToTarget(Double amount, String targetCurrency, ExchangeRate exchangeRates) throws ExchangeRateNotFoundException {
        Double convertedAmount;
        convertedAmount = amount * exchangeRates.getCurrencyRate(targetCurrency);
        return roundTo2DecimalPlaces(convertedAmount);
    }

    private Double roundTo2DecimalPlaces(Double number){
        DecimalFormat format = new DecimalFormat("##.00");
        return Double.parseDouble(format.format(number));
    }


}
