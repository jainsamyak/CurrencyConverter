package com.example.currencyconverter.service;

import com.example.currencyconverter.dto.ExchangeRate;
import com.example.currencyconverter.exception.ExchangeRateNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class ExchangeRatesService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesService.class);
    private final String CACHE_NAME = "exchangeRate";

    @Value("${EXCHANGE_RATES_API_KEY}")
    private String apiKey;

    @Value("${EXCHANGE_RATES_API_URL}")
    private String apiUrl;

    private final RPCService rpcService;
    private final CacheManager cacheManager;

    @Autowired
    public ExchangeRatesService(RPCService rpcService, CacheManager cacheManager){
        this.rpcService = rpcService;
        this.cacheManager = cacheManager;
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


    // Schedule Cache Eviction for every 1 hour
    @Scheduled(fixedDelay = 60 * 1000)
    public void evictCacheScheduler(){
        logger.info("Evicting cache for " + CACHE_NAME);
        this.cacheManager.getCache(CACHE_NAME).clear();
    }


}
