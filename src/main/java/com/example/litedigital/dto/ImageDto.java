package com.example.litedigital.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageDto {
    public Object object;
    public String path;
    public LocalDateTime created;
    public long memory;
}
