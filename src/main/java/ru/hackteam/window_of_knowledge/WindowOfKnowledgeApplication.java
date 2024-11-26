package ru.hackteam.window_of_knowledge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class WindowOfKnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindowOfKnowledgeApplication.class, args);
    }

}
