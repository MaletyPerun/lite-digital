package com.example.litedigital.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {
    private String host;
    private Integer port;
    private String login;
    private String password;
    private String dirName;
    private String prefixFile;
}
