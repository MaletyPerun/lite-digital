package com.example.litedigital.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FTP {
    //    @Value("${project.host}")
    private String host = "185.27.134.11";
    //    @Value("${project.port}")
    private int port = 21;
    //    @Value("${project.login}")
    private String username = "epiz_33891104";
    //    @Value("${project.password}")
    private String password = "CLc195rPV8h3cv";
    private String dirName = "фотографии";
    private String fileName = "GRP327_";

//    public FTP(String dirName, String fileName) {
//        this.dirName = dirName;
//        this.fileName = fileName;
//    }

    public List<FTPFile> getFiles() {
        //  String path = ftpClient.printWorkingDirectory() + "/" + file.getName();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date timestamp = file.getTimestamp().getTime();
//        long size = file.getSize();
        FTPClient client = new FTPClient();

        try {
            client.connect(host, port);
            client.login(username, password);

            Arrays.stream(client.listDirectories())
                    .forEach(d -> findDirs(client, ""));


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void findDirs(FTPClient ftpClient, String dirName) {
        FTPFile[] directories = new FTPFile[0];
        try {
            ftpClient.changeWorkingDirectory(ftpClient.printWorkingDirectory() + "/" + dirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            directories = ftpClient.listDirectories();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (directories.length != 0) {
            Arrays.stream(directories)
                    .forEach(d -> findDirs(ftpClient, d.getName()));
            Arrays.stream(directories)
                    .filter(d -> d.getName().equals(dirName))
                    .forEach(d -> findFiles(ftpClient, d.getName()));
        }
    }

    private void findFiles(FTPClient ftpClient, String name) {
        FTPFile[] files = new FTPFile[0];
        try {
            ftpClient.changeWorkingDirectory(ftpClient.printWorkingDirectory() + "/" + dirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            files = ftpClient.listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Arrays.stream(files)
//                .filter(f -> f.getName().startsWith(fileName))
//                .forEach(f -> targets.add(f.getName()));
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
//        try {
//            client.changeWorkingDirectory(String.valueOf(directories.get(0)));
//            System.out.println(Arrays.toString(client.listDirectories()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (FTPFile dir : directories) {
            dirNames.add(dir.getName());
        }
        return dirNames;
    }
}
