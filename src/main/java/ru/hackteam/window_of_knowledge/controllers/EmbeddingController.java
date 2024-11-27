package ru.hackteam.window_of_knowledge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hackteam.window_of_knowledge.services.EmbeddingService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {

    private final EmbeddingService embeddingService;

    @Autowired
    public EmbeddingController(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    @PostMapping("/process")
    public String processText(@RequestParam String id, @RequestParam String text) {
        try {
            embeddingService.processText(id, text);
            return "Text processed and saved!";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> getCompanyData(@PathVariable String id) {
        return embeddingService.getCompanyData(id);
    }
}
