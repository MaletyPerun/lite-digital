package com.example.litedigital.controller;

import com.example.litedigital.dto.PhotoDto;
import com.example.litedigital.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PhotoDto> getFiles() {
        return service.getFiles();
    }
}
