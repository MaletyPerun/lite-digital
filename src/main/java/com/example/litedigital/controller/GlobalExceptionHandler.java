package com.example.litedigital.controller;

import com.example.litedigital.ftp.BadConnection;
import com.example.litedigital.ftp.BadDisconnection;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler(BadConnection.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    protected ResponseEntity<Object> handleConflict(WebRequest request, BadConnection e) {
        return createResponseEntity(request, e.getOptions(), e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(BadDisconnection.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    protected ResponseEntity<Object> handleConflict(WebRequest request, BadDisconnection e) {
        return createResponseEntity(request, e.getOptions(), e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }

    @SuppressWarnings("unchecked")
    protected <T> ResponseEntity<T> createResponseEntity(WebRequest request, ErrorAttributeOptions options, String msg, HttpStatus status) {
        Map<String, Object> body = errorAttributes.getErrorAttributes(request, options);
        body.put("message", msg);
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        return (ResponseEntity<T>) ResponseEntity.status(status).body(body);
    }
}
