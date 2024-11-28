package ru.hackteam.window_of_knowledge.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class DocxService {
    private EmbeddingService embeddingService;

    public DocxService(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    public List<String> processDocxFile(MultipartFile docxFile) throws IOException, InterruptedException {
        List<String> textChunks = new ArrayList<>();
        try (InputStream inputStream = docxFile.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream)) {

            StringBuilder textBuilder = new StringBuilder();

            // Извлекаем текст из всех параграфов документа
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                textBuilder.append(paragraph.getText()).append(" ");
            }

            // Получаем полный текст и делим его на слова
            String fullText = textBuilder.toString().trim();
            String[] words = fullText.split("\\s+");

            // Если меньше 200 слов, добавляем весь текст как одну часть
            if (words.length <= 200) {
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
                        chunk.setLength(0); // Очищаем для новой части
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
            textChunks.add("Ошибка при обработке файла: " + e.getMessage());
        }
        for (String ch : textChunks) {
            embeddingService.processText("1", ch);
        }

        return textChunks;
    }
}