package ru.hackteam.window_of_knowledge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hackteam.window_of_knowledge.services.EmbeddingService;

import java.io.IOException;
import java.util.List;
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
    public List<Map<String, Object>> getEmbeddings(@PathVariable String id) {
        return embeddingService.getEmbeddings(id);
    }

    @GetMapping("/find-relevant")
    public List<Map<String, Object>> findRelevantTexts(@RequestParam String id, @RequestParam String text) {
        try {
            return embeddingService.findTopRelevantTexts(id, text);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing relevance: " + e.getMessage());
        }
    }
}
