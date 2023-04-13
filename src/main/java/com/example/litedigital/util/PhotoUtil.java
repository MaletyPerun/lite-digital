package com.example.litedigital.util;

import com.example.litedigital.dto.PhotoDto;
import com.example.litedigital.model.ProjectFile;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PhotoUtil {

    public List<PhotoDto> modelToDto(List<ProjectFile> targets) {
        return targets.stream()
                .map(s -> PhotoDto.builder()
                        .fullPath(s.getPath() + "/" + s.getFile().getName())
                        .created(s.getFile().getTimestampInstant())
                        .memory(s.getFile().getSize())
                        .build())
                .collect(Collectors.toList());
    }
}
