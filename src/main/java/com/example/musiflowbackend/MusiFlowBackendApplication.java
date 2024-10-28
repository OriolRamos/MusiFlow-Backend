package com.example.musiflowbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusiFlowBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusiFlowBackendApplication.class, args);
        System.out.println("Aplicació de backend iniciada amb èxit.");
    }
}
