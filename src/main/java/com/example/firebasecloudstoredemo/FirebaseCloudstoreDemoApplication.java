package com.example.firebasecloudstoredemo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class FirebaseCloudstoreDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirebaseCloudstoreDemoApplication.class, args);
    }

}


