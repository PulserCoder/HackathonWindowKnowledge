package ru.hackteam.window_of_knowledge.api_openai;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ChatGPTClient {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("API_KEY");
    private static final String MODEL = "gpt-4";

    public static String sendRequest(List<Map<String, Object>> conversationHistory) throws Exception {
        String requestBody = createRequestBody(conversationHistory);
        HttpResponse<String> response = sendHttpRequest(requestBody);
        return handleResponse(response, conversationHistory);
    }

    private static String createRequestBody(List<Map<String, Object>> conversationHistory) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Map.of(
                "model", MODEL,
                "messages", conversationHistory
        ));
    }

    private static HttpResponse<String> sendHttpRequest(String requestBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String handleResponse(HttpResponse<String> response, List<Map<String, Object>> conversationHistory) throws Exception {
        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(response.body(), Map.class);
            Map<String, Object> choice = (Map<String, Object>) ((List<Object>) responseMap.get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) choice.get("message");

            // Добавляем ответ модели в историю
            conversationHistory.add(Map.of(
                    "role", "assistant",
                    "content", message.get("content")
            ));

            return (String) message.get("content");
        } else {
            throw new RuntimeException("API Error: " + response.body());
        }
    }
}
