package ru.hackteam.window_of_knowledge.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {

    public String convertPdfToText(MultipartFile pdfFile, int startPage, int endPage) {
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

        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при обработке PDF файла: " + e.getMessage();
        }

        return extractedText;
    }
}
