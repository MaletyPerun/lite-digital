package com.example.litedigital.controller;

import com.example.litedigital.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getFiles() {
        return service.getFiles();
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public List<String> testConnection() {
        return service.test();
    }
}
