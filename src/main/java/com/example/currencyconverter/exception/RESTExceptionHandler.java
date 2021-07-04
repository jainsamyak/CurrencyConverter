package com.example.currencyconverter.exception;

import com.example.currencyconverter.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing!";
        return ErrorResponse.sendResponse(HttpStatus.BAD_REQUEST, error, ex.getClass().getSimpleName(), error, headers);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        try {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.add(violation.getMessage());
            }

            return ErrorResponse.sendResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getClass().getSimpleName(), errors, null);
        } catch (Exception e) {
            return ErrorResponse.sendResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), ex.getClass().getSimpleName(), ex.getMessage(), null);
        }
    }

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public final ResponseEntity<Object> handleExchangeRateNotFoundException(Exception ex) {
        return ErrorResponse.sendResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getClass().getSimpleName(), ex.getMessage(),null);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request, HttpHeaders headers) {
        return ErrorResponse.sendResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getClass().getSimpleName(), ex.getMessage(), headers);
    }


}
