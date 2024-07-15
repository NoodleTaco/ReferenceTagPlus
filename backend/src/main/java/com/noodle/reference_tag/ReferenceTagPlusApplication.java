package com.noodle.reference_tag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ReferenceTagPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReferenceTagPlusApplication.class, args);
    }

}