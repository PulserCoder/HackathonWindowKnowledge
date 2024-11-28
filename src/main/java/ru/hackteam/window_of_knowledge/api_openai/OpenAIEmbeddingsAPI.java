package ru.hackteam.window_of_knowledge.api_openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
public class OpenAIEmbeddingsAPI {
    private final ObjectMapper objectMapper;

    @Value("${API_KEY}")
    private String API_KEY;

    @Value(value = "${api.url.embeddings}")
    private String API_URL;

    private final HttpClient httpClient;


    public OpenAIEmbeddingsAPI() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }


    public double[] getEmbeddings(String text) throws IOException, InterruptedException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("input", text);
        requestBody.put("model", "text-embedding-ada-002");

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Ошибка при запросе к OpenAI API: " + response.body());
        }

        Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);
        Map<String, Object> data = (Map<String, Object>) ((java.util.List<?>) responseMap.get("data")).get(0);
        java.util.List<Double> embeddingList = (java.util.List<Double>) data.get("embedding");

        return embeddingList.stream().mapToDouble(Double::doubleValue).toArray();


    }
}
