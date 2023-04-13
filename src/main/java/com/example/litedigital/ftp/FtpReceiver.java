package com.example.litedigital.ftp;

import com.example.litedigital.config.ProjectProperties;
import com.example.litedigital.model.ProjectFile;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
@Slf4j
public class FtpReceiver {
    private FTPClient client;
    private final ProjectProperties projectProperties;

    @PostConstruct
    private void init() {
        client = new FTPClient();
        client.setControlEncoding("UTF-8");
        log.info("encoding {}", client.getControlEncoding());
        try {
            client.connect(projectProperties.getHost(), projectProperties.getPort());
            showServerReply(client);
            int replyCode = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                client.disconnect();
                System.out.println("Connect failed");
                return;
            }
            boolean success = client.login(projectProperties.getLogin(), projectProperties.getPassword());
            showServerReply(client);
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            }

            client.addProtocolCommandListener(
                    new PrintCommandListener(
                            new PrintWriter(new OutputStreamWriter(System.out, UTF_8)), true));
//            PrintCommandListener listener = new PrintCommandListener(System.out);
//            client.addProtocolCommandListener(listener);
//            client.setControlEncoding("CP1251");
            client.setFileType(FTP.BINARY_FILE_TYPE);
//            client.enterLocalPassiveMode();
        } catch (IOException e) {
            log.error("failed connection or login");
            throw new BadConnection("failed connection or login");
        }
    }

    @PreDestroy
    private void preDestroy() {
        try {
            client.logout();
            client.disconnect();
        } catch (IOException e) {
            log.error("failed disconnection");
            throw new BadDisconnection("failed disconnection");
        }
    }

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

    public List<ProjectFile> getFiles(String path, String dirName, String prefixFile) {
        List<ProjectFile> fileList = new ArrayList<>();
        FTPFile[] files = new FTPFile[0];
//        String pathEncoding = Path
//        Path encodePath = Path.of(path);
        try {
//            files = client.listFiles(path);
//            client.printWorkingDirectory();
            client.changeWorkingDirectory(path);
            client.printWorkingDirectory();
            files = client.listFiles();
        } catch (IOException e) {
            log.error("failed connection: can`t read directories");
            throw new BadConnection("failed connection: can`t read directories");
        }
        for (FTPFile file : files) {
            if (file.isDirectory() && file.getName().equals(dirName)) {
                List<ProjectFile> subFiles = getTarget(path + "/" + file.getName(), prefixFile);
                fileList.addAll(subFiles);
            } else if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals("..")) {
                log.info("found directory {}", file.getName());
                String pathParent = !path.equals("") ? path + "/" + file.getName() : "/" + file.getName();
                List<ProjectFile> subFiles = getFiles(pathParent, dirName, prefixFile);
                fileList.addAll(subFiles);
            }
        }
        return fileList;
    }

    private List<ProjectFile> getTarget(String path, String prefixFile) {
        List<ProjectFile> fileList = new ArrayList<>();
        try {
            client.changeWorkingDirectory(path);
        } catch (IOException e) {
            log.error("failed connection: can`t read directories");
            throw new BadConnection("failed connection: can`t read directories");
        }
        FTPFileFilter filter = file -> file.isFile() && file.getName().startsWith(prefixFile);
        FTPFile[] files = new FTPFile[0];
//        Path encodePath = Path.of(path);
        try {
            files = client.mlistDir(path, filter);
            client.printWorkingDirectory();
        } catch (IOException e) {
            log.error("bad connection: can`t read files");
            throw new BadConnection("bad connection: can`t read files");
        }
        for (FTPFile file : files) {
//            if (file.isFile() && file.getName().startsWith(prefixFile)) {
                log.info("add {}", file.getName());
                fileList.add(new ProjectFile(file, path));
//            }
        }
        return fileList;
    }

    public List<String> test() {
        FTPFile[] files = new FTPFile[0];
        try {
            files = client.listFiles("htdocs/ftp/Фотозона 1");
        } catch (IOException e) {
            log.error("fail test");
            throw new BadConnection("fail test");
        }
        List<String> dirNames = new ArrayList<>();
        for (FTPFile dir : files) {
            dirNames.add(dir.getName());
        }
        return dirNames;
    }
}
