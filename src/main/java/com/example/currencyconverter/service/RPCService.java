package com.example.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RPCService {
    private RestTemplate restTemplate;

    public RPCService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    public String get(String url){
        String result = restTemplate.getForObject(url, String.class);
        return result;
    }
}
