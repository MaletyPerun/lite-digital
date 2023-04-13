package com.example.litedigital.ftp;

import com.example.litedigital.controller.AppException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class BadDisconnection extends AppException {
    public BadDisconnection(Object message) {
        super(HttpStatus.I_AM_A_TEAPOT, message.toString(), ErrorAttributeOptions.of(MESSAGE));
    }
}
