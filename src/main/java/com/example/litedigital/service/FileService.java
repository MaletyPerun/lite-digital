package com.example.litedigital.service;

import com.example.litedigital.ftp.FTP;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    //    @Autowired
    private final FTP ftp;

    static List<String> targets = new ArrayList<>();

    public List<String> getFiles() {
//        FTP ftp = new FTP("фотографии", "GRP327_");
        List<FTPFile> ftpFiles = ftp.getFiles();


        return targets;
    }

//    public List<String> test() {
//        FTP ftp = new FTP();
//        return ftp.test();
//    }
}
