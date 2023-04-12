package com.example.litedigital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.net.ftp.FTPFile;

@AllArgsConstructor
@Getter
public class ProjectFile {
    private FTPFile file;
    private String path;
}
