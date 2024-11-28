package ru.hackteam.window_of_knowledge.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class UrlTextService {

    public String getTextFromUrl(String urlString) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            // Создаем URL-объект и открываем соединение
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Проверяем статус ответа (200 — успешный запрос)
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line).append("\n");
                    }
                }
            } else {
                result.append("Ошибка подключения: ").append(responseCode);
            }

        } catch (IOException e) {
            result.append("Ошибка при получении текста с URL: ").append(e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result.toString();
    }
}