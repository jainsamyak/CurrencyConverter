package com.example.currencyconverter.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private Boolean success;
    private String message;
    private Object error;

    public static ResponseEntity sendResponse(HttpStatus statusCode, String message, String exceptionName, Object error, HttpHeaders headers){
        Map<String, Object> responseError = new HashMap<>();
        responseError.put("details", error);
        responseError.put("name", exceptionName);

        return new ResponseEntity<>(new ErrorResponse(false, message, responseError), headers, statusCode);
    }
}
