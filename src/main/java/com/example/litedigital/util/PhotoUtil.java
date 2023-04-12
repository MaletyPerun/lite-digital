package com.example.litedigital.util;

import com.example.litedigital.dto.PhotoDto;
import com.example.litedigital.model.ProjectFile;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PhotoUtil {

    public List<PhotoDto> modelToDto(List<ProjectFile> targets) {
        List<PhotoDto> targetsDto = new ArrayList<>();
        for (ProjectFile target : targets) {
            targetsDto.add(PhotoDto.builder()
                    .fullPath(target.getPath() + target.getFile().getName())
                    .created(target.getFile().getTimestampInstant())
                    .memory(target.getFile().getSize())
                    .build());
        }
        return targetsDto;
    }
}
