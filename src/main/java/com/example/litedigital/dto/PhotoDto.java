package com.example.litedigital.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PhotoDto {
    public String fullPath;
    public Instant created;
    public long memory;
}
