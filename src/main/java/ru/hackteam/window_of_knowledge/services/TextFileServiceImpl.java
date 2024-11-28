package ru.hackteam.window_of_knowledge.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextFileServiceImpl {

    public List<String> saveDataToBd(MultipartFile txtFile) {
        List<String> textChunks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(txtFile.getInputStream()))) {
            StringBuilder textBuilder = new StringBuilder();
            String line;

            // Считываем весь текст из файла
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line).append(" ");
            }

            // Разделяем текст на слова
            String fullText = textBuilder.toString().trim();
            String[] words = fullText.split("\\s+");

            if (words.length <= 200) {
                // Если меньше 200 слов, добавляем весь текст как одну часть
                textChunks.add(fullText);
            } else {
                // Если больше 200 слов, делим на части по 200 слов
                StringBuilder chunk = new StringBuilder();
                int wordCount = 0;

                for (String word : words) {
                    chunk.append(word).append(" ");
                    wordCount++;

                    if (wordCount == 200) {
                        textChunks.add(chunk.toString().trim());
                        chunk.setLength(0);  // очищаем для следующей части
                        wordCount = 0;
                    }
                }

                // Добавляем остаток слов, если он есть
                if (chunk.length() > 0) {
                    textChunks.add(chunk.toString().trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            textChunks.add("Ошибка при чтении файла: " + e.getMessage());
        }

        return textChunks;
    }
}
