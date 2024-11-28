package ru.hackteam.window_of_knowledge.services;

import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.models.TextData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextServiceImpl {
    private EmbeddingService embeddingService;
    public TextServiceImpl(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    public List<String> saveTextToBd(TextData textData) throws IOException, InterruptedException {
        String text = textData.getTextData();
        String[] words = text.split("\\s+"); // Разделение строки на слова
        List<String> chunks = new ArrayList<>();

        StringBuilder chunk = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            chunk.append(word).append(" ");
            wordCount++;

            // Если достигли 200 слов или конец текста, добавляем в список
            if (wordCount == 200) {
                chunks.add(chunk.toString().trim());
                chunk.setLength(0); // Очистка StringBuilder
                wordCount = 0;
            }
        }

        // Добавляем последний оставшийся кусок, если он не пуст
        if (chunk.length() > 0) {
            chunks.add(chunk.toString().trim());
        }
        for (String ch : chunks) {
            embeddingService.processText("1", ch);
        }
        return chunks;
    }
}