package ru.hackteam.window_of_knowledge.models;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class ChatGPTClient {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;

    public ChatGPTClient() {
        String apiKey = "API_KEY_CHATGPT";
        apiKey = System.getenv(apiKey);
        this.apiKey = apiKey;
    }

    public String sendMessage(String model, String prompt) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(Map.of("model", model, "messages", new Object[]{Map.of("role", "user", "content", prompt)}));

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).header("Content-Type", "application/json").header("Authorization", "Bearer " + apiKey).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Map<String, Object> responseMap = mapper.readValue(response.body(), Map.class);
            Map<String, Object> choice = (Map<String, Object>) ((List<Object>) responseMap.get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) choice.get("message");
            return (String) message.get("content");
        } else {
            throw new RuntimeException("API Error: " + response.body());
        }
    }

    public static void main(String[] args) {
        // образец использования
        ChatGPTClient client = new ChatGPTClient();
        try {
            String response = client.sendMessage("gpt-4", "как дела?");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

