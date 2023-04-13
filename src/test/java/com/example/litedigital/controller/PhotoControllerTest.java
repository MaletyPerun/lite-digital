package com.example.litedigital.controller;

import com.example.litedigital.config.ProjectProperties;
import com.example.litedigital.ftp.FtpReceiver;
import com.example.litedigital.model.ProjectFile;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    public ProjectProperties projectProperties;

    @MockBean
    public FtpReceiver client;

    @Test
    void updateEntity() {
        List<ProjectFile> testFiles = new ArrayList<>();
        FTPFile testFile = new FTPFile();
        testFile.setName("GRP327_testPhoto");
        testFile.setSize(500L);
        testFile.setTimestamp(Calendar.getInstance());
        testFiles.add(new ProjectFile(testFile, "фотографии"));
        when(client.getFiles("", projectProperties.getDirName(), projectProperties.getPrefixFile())).thenReturn(testFiles);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> request = restTemplate.exchange("/photo", HttpMethod.GET, new HttpEntity<>(headers), String.class);
        assertTrue(Objects.requireNonNull(request.getBody()).contains("фотографии/GRP327_testPhoto"));
        assertEquals(HttpStatus.OK, request.getStatusCode());
    }
}