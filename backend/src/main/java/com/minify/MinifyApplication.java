package com.minify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.minify.repository")
@EntityScan(basePackages = "com.minify.entity")
public class MinifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinifyApplication.class, args);
    }
}