package com.example.currencyconverter;

import com.example.currencyconverter.interceptor.RequestExcecutionTimeInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class CurrencyConverterApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestExcecutionTimeInterceptor()).addPathPatterns("/**");
    }
}
