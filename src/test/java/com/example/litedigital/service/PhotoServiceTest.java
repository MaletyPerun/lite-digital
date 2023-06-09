package com.example.litedigital.service;

import com.example.litedigital.config.ProjectProperties;
import com.example.litedigital.dto.PhotoDto;
import com.example.litedigital.ftp.FtpReceiver;
import com.example.litedigital.model.ProjectFile;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoServiceTest {

    @Autowired
    public ProjectProperties projectProperties;

    @MockBean
    public FtpReceiver client;

    @Autowired
    public PhotoService service;

    @Test
    void getFilesFromMockFtp() {
        List<ProjectFile> testFiles = new ArrayList<>();
        FTPFile testFile = new FTPFile();
        testFile.setName("GRP327_testPhoto");
        testFile.setSize(500L);
        testFile.setTimestamp(Calendar.getInstance());
        testFiles.add(new ProjectFile(testFile, "фотографии"));
        when(client.getFiles("", projectProperties.getDirName(), projectProperties.getPrefixFile())).thenReturn(testFiles);
        List<PhotoDto> testPhotoDto = service.getFiles();
        assertEquals(testPhotoDto.get(0).getFullPath(), "фотографии/GRP327_testPhoto");
        assertEquals(testPhotoDto.get(0).getMemory(), 500L);
    }
}