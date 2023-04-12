package com.example.litedigital;

import com.example.litedigital.config.ProjectProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(ProjectProperties.class)
public class LiteDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteDigitalApplication.class, args);
    }

}
