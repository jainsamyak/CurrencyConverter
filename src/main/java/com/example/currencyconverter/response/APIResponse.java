package com.example.currencyconverter.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
@Setter
public class APIResponse {
    private Boolean success;
    private Object data;


    public static ResponseEntity sendResponse(HttpStatus statusCode, Object data, HttpHeaders headers){
        return new ResponseEntity<>(new APIResponse(true, data), headers, statusCode);
    }
}
