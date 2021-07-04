package com.example.currencyconverter.utils;

import org.apache.commons.math3.util.Precision;

import java.text.DecimalFormat;

public class Utilities {

    public static String convertToUpperCase(String s){
        return s.toUpperCase();
    }

    public static Double roundNumber(Double number, Integer decimalPlaces){
        if(Double.isNaN(number)) return 0.0;

        return Precision.round(number, decimalPlaces);
    }

    public static Double roundNumber(Double number){
        if(Double.isNaN(number)) return 0.0;

        return Precision.round(number, 2);
    }
}
