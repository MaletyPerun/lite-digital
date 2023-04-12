package com.example.litedigital.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

@Data
@Builder
@ToString
public class PhotoDto {
    public String fullPath;
    public Instant created;
    public long memory;
}
