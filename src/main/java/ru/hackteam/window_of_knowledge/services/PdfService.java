package ru.hackteam.window_of_knowledge.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfService {

    private EmbeddingService embeddingService;

    public PdfService(EmbeddingService embeddingService) {
        this.embeddingService = embeddingService;
    }

    public List<String> convertPdfToText(MultipartFile pdfFile, int startPage, int endPage) throws IOException, InterruptedException {

        String extractedText = "";
        try (InputStream inputStream = pdfFile.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Если startPage и endPage не указаны, обрабатываем весь файл
            if (startPage != 0) {
                pdfStripper.setStartPage(startPage);
            } else {
                pdfStripper.setStartPage(1);
            }

            if (endPage != 0) {
                pdfStripper.setEndPage(endPage);
            } else {
                pdfStripper.setEndPage(document.getNumberOfPages());
            }

            // Извлечение текста из PDF
            extractedText = pdfStripper.getText(document);

        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        String[] words = extractedText.split("\\s+"); // Разделение строки на слова
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

        if (chunk.length() > 0) {
            chunks.add(chunk.toString().trim());
        }
        for (String s : chunks) {
            embeddingService.processText("1", s);
        }
        return chunks;
    }
}