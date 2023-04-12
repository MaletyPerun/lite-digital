package com.example.litedigital.service;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {

    static List<String> targets = new ArrayList<>();
    @Value("${project.host}")
    private String host;
    @Value("${project.port}")
    private int port;
    @Value("${project.login}")
    private String username;
    @Value("${project.password}")
    private String password;

    public List<String> getFiles() {

        FTPClient client = new FTPClient();

        try {
            client.connect(host, port);
            client.login(username, password);

            Arrays.stream(client.listDirectories())
                    .forEach(d -> findFilesAndDirs(client, d.getLink()));


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targets;
    }

    public void findFilesAndDirs(FTPClient ftpClient, String dirName) {
        FTPFile[] directories = new FTPFile[0];
        try {
            directories = ftpClient.listDirectories(dirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (directories.length != 0) {
            Arrays.stream(directories)
                    .forEach(d -> findFilesAndDirs(ftpClient, d.getName()));
            Arrays.stream(directories)
                    .filter(d -> d.getName().equals("фотографии"))
                    .forEach(d -> findFiles(ftpClient, d.getName()));
        }
    }

    private void findFiles(FTPClient ftpClient, String link) {
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Arrays.stream(files)
                .filter(f -> f.getName().startsWith("GRP327_"))
                .forEach(f -> targets.add(f.getName()));
    }

    public List<String> test() {
        FTPClient client = new FTPClient();
        List<FTPFile> directories = new ArrayList<>();
        List<String> dirNames = new ArrayList<>();

        try {
            client.connect(host, port);
            client.login(username, password);
            directories = List.of(client.listDirectories());

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (FTPFile dir : directories) {
            dirNames.add(dir.getName());
        }
        return dirNames;
    }
}
