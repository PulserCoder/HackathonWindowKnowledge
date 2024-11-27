package ru.hackteam.window_of_knowledge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.models.Question;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AnswerService {

    private final EmbeddingService embeddingService;


    public AnswerService(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    public String getAnswer(Question question, String assitantId, String userId) throws IOException, InterruptedException {
        List<Map<String, Object>> texts = embeddingService.findTopRelevantTexts(assitantId, question.getGuestion());
        return "";
    }
}
