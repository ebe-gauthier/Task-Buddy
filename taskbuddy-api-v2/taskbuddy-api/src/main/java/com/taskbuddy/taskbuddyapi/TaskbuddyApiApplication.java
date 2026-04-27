package com.taskbuddy.taskbuddyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TaskbuddyApiApplication — the entry point of the Spring Boot application.
 *
 * @SpringBootApplication combines three annotations:
 *   - @Configuration       : marks this as a config class
 *   - @EnableAutoConfiguration : lets Spring Boot auto-configure beans
 *   - @ComponentScan       : scans for @Controller, @Service, @Repository, etc.
 *
 * Just run main() and your server starts on http://localhost:8080
 */
@SpringBootApplication
public class TaskbuddyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskbuddyApiApplication.class, args);
    }
}
