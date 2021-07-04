package com.example.currencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class CurrencyExchange {
    private String source;
    private String target;
    private Double amount;
    private String result;
}
