package com.example.currencyconverter.controller;

import com.example.currencyconverter.dto.CurrencyExchange;
import com.example.currencyconverter.response.APIResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.currencyconverter.service.CurrencyConverterService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@RestController
@Validated
@RequestMapping("/api")
public class CurrencyConverterController {
    private final CurrencyConverterService currencyConverterService;

    @Autowired
    public CurrencyConverterController(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity convertCurrency(@RequestParam(name = "source") @Size(min = 3, max = 3, message = "source should be exactly {min} characters long") String sourceCurrency, @RequestParam(name = "target") String targetCurrency, @RequestParam(name = "amount") @Min(value = 0, message = "amount should be greater than {value}") Double currencyAmount){
        CurrencyExchange currencyExchange = currencyConverterService.convertCurrency(sourceCurrency, targetCurrency, currencyAmount);
        return APIResponse.sendResponse(HttpStatus.OK, currencyExchange, null);
    }
}
