package ru.hackteam.window_of_knowledge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.models.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnswerService {

    private final EmbeddingService embeddingService;
    private final ChatGPTDialog chatGPTDialog;


    public AnswerService(EmbeddingService embeddingService, ChatGPTDialog chatGPTDialog) {
        this.embeddingService = embeddingService;
        this.chatGPTDialog = chatGPTDialog;
    }

    public String getAnswer(Question question, String assitantId, String userId) throws Exception {
        List<Map<String, Object>> texts = embeddingService.findTopRelevantTexts(assitantId, question.getGuestion());
        List<String> textsForFunc = new ArrayList<>();
        for (Map<String, Object> text : texts) {
            textsForFunc.add(text.get("text").toString());
        }
        String relevantText = chatGPTDialog.getRelevantAnswer(question.getGuestion(), textsForFunc);
        String prompt = String.format(
                "Hi! Imagine that I'm asking you the question \"%s\" Please answer in Russian ALWAYS, and imagine that you are a manager who helps, and takes all the answers from the text that I will give you below. ALWAYS ANSWER ON RUSSIAN AND IF IN TEXT NO ANSWER YOU NEED TO ANSWER AS YOU CAN. Here is the text where you need to get the answer from: %s\n\n",
                question.getGuestion(),
                relevantText
        );
        System.out.println("prompt = " + prompt);
        return chatGPTDialog.sendMessage(prompt, userId, question.getGuestion());
    }
}
