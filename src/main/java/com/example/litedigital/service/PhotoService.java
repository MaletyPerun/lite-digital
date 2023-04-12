package com.example.litedigital.service;

import com.example.litedigital.config.ProjectProperties;
import com.example.litedigital.dto.PhotoDto;
import com.example.litedigital.ftp.FtpReceiver;
import com.example.litedigital.model.ProjectFile;
import com.example.litedigital.util.PhotoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final ProjectProperties properties;

    private final FtpReceiver ftp;

    public List<PhotoDto> getFiles() {
        List<ProjectFile> targets = ftp.getFiles("", properties.getDirName(), properties.getPrefixFile());
        return PhotoUtil.modelToDto(targets);
    }
    public List<String> test() {
        return ftp.test();
    }
}
