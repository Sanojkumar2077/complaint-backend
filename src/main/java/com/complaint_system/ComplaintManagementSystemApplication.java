package com.complaint_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.smartcomplaint")
@EnableJpaRepositories(basePackages = "com.smartcomplaint.repository")
@EntityScan(basePackages = "com.smartcomplaint.entity")
public class ComplaintManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComplaintManagementSystemApplication.class, args);
    }
}
