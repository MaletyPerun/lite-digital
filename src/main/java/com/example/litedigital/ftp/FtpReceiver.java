package com.example.litedigital.ftp;

import com.example.litedigital.config.ProjectProperties;
import com.example.litedigital.model.ProjectFile;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FtpReceiver {
    private FTPClient client;
    private final ProjectProperties projectProperties;

    @PostConstruct
    private void init() {
        client = new FTPClient();
        try {
            client.connect(projectProperties.getHost(), projectProperties.getPort());
            client.login(projectProperties.getLogin(), projectProperties.getPassword());
            client.setControlEncoding("UTF-8");
        } catch (IOException e) {
            log.error("failed connection or login");
        }
    }

    @PreDestroy
    private void preDestroy() {
        try {
            client.disconnect();
        } catch (IOException e) {
            log.error("failed disconnection");
        }
    }

    public List<ProjectFile> getFiles(String path, String dirName, String prefixFile) {
        List<ProjectFile> fileList = new ArrayList<>();
        FTPFile[] files = new FTPFile[0];
        try {
            files = client.listFiles(path);
        } catch (IOException e) {
            log.error("failed connection");
        }
        for (FTPFile file : files) {
            if (file.isDirectory() && file.getName().equals(dirName)) {
//                List<ProjectFile> subFiles = getTarget(path + "/" + file.getName(), prefixFile);
                List<ProjectFile> subFiles = getTarget(file.getName(), prefixFile);
                fileList.addAll(subFiles);
            } else if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
//                List<ProjectFile> subFiles = getFiles(path + "/" + file.getName(), dirName, prefixFile);

                String pathParent = !path.equals("") ? path + "/" + file.getName() : file.getName();
                List<ProjectFile> subFiles = getFiles(pathParent, dirName, prefixFile);
                fileList.addAll(subFiles);
            }
        }
        return fileList;
    }

    private List<ProjectFile> getTarget(String path, String prefixFile) {
        List<ProjectFile> fileList = new ArrayList<>();
        FTPFileFilter filter = file -> file.isFile() && file.getName().equals(prefixFile);
        FTPFile[] files = new FTPFile[0];
        try {
            files = client.mlistDir(path, filter);
        } catch (IOException e) {
            log.error("bad connection");
        }
        for (FTPFile file : files) {
            if (file.isFile() && file.getName().startsWith(prefixFile)) {

                fileList.add(new ProjectFile(file, path));
            }
        }
        return fileList;
    }

    public List<String> test() {
//        FTPClient client = new FTPClient();
        FTPFile[] files = new FTPFile[0];
        try {
            files = client.listFiles("htdocs/ftp");
        } catch (IOException e) {
            log.error("fail test");
        }
        List<FTPFile> directories = new ArrayList<>();
        List<String> dirNames = new ArrayList<>();

        for (FTPFile dir : files) {
            dirNames.add(dir.getName());
        }
        return dirNames;
    }
}
