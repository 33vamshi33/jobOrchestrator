package com.vamshi.jobOrchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.vamshi.jobOrchestrator")
public class JobOrchestratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobOrchestratorApplication.class, args);
    }
}

