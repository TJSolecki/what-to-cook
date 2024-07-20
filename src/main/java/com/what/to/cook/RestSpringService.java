package com.what.to.cook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.what.to.cook")
@EnableJdbcRepositories
public class RestSpringService {

    public static void main(String[] args) {
        SpringApplication.run(RestSpringService.class, args);
    }

}
