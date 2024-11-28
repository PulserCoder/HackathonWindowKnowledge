package ru.hackteam.window_of_knowledge.api_openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public class ChatGPTClient {

    @Value("${base_url}")
    private String baseUrl;

    @Value("${API_KEY}")
    private String API_KEY;

    private static final String MODEL = "gpt-4";


    public String sendRequest(List<Map<String, Object>> conversationHistory) throws Exception {
        String requestBody = createRequestBody(conversationHistory);
        HttpResponse<String> response = sendHttpRequest(requestBody);
        return handleResponse(response, conversationHistory);
    }

    private String createRequestBody(List<Map<String, Object>> conversationHistory) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Map.of(
                "model", MODEL,
                "messages", conversationHistory,
                "temperature", 0.1)
        );
    }

    private HttpResponse<String> sendHttpRequest(String requestBody) throws Exception {
        System.out.println("baseUrl + \"/v1/chat/completions\" = " + baseUrl + "/v1/chat/completions");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String handleResponse(HttpResponse<String> response, List<Map<String, Object>> conversationHistory) throws Exception {
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