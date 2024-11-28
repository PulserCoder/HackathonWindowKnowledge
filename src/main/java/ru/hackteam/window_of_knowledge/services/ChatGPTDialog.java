package ru.hackteam.window_of_knowledge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.api_openai.ChatGPTClient;
import ru.hackteam.window_of_knowledge.models.ChatGPTConversation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ChatGPTDialog {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatGPTClient chatGPTClient;

    @Autowired
    public ChatGPTDialog(
            @Qualifier("saveConversation") RedisTemplate<String, Object> redisTemplate, ChatGPTClient chatGPTClient) {
        this.redisTemplate = redisTemplate;
        this.chatGPTClient = chatGPTClient;
    }

    public String getRelevantAnswer(String question, List<String> answers) {
        StringBuilder result = new StringBuilder();
        for (String answer : answers) {
            System.out.println("answer = " + answer);
        }
        // Создаем список сообщений для текущего запроса
        List<Map<String, Object>> conversationHistory = new ArrayList<>();

        // Добавляем системное сообщение с инструкцией
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "Твоя задача - проверить, есть ли ответ на вопрос в тексте. Если есть, отправь '+', если нет, отправь '-'");
        conversationHistory.add(systemMessage);

        for (String answer : answers) {
            try {
                // Добавляем пользовательское сообщение с вопросом и ответом
                Map<String, Object> userMessage = new HashMap<>();
                userMessage.put("role", "user");
                userMessage.put("content", "Текст: " + "\n" +  answer + "\n" + "Вопрос:" + question);
                conversationHistory.add(userMessage);

                // Отправляем запрос в GPT
                String gptResponse = chatGPTClient.sendRequest(conversationHistory);
                // Добавляем ответ GPT к результатам
                if (gptResponse.contains("+")) {
                    result.append(" ");
                    result.append(answer);
                }

            } catch (Exception e) {
                e.printStackTrace();
                result.append("E");
            }
        }
        System.out.println("result = " + result);
        return result.toString();
    }

    public String sendMessage(String prompt, String conversationId, String question) throws Exception {
        List<Map<String, Object>> conversationHistory = getConversationHistory(conversationId);

        Map<String, Object> modifiedMessage = Map.of(
                "role", "system",
                "content", prompt
        );
        conversationHistory.add(modifiedMessage);

        Map<String, Object> modifiedMessage1 = Map.of(
                "role", "user",
                "content", question
        );
        conversationHistory.add(modifiedMessage1);
        String response = chatGPTClient.sendRequest(conversationHistory);
        addAssistantMessageToHistory(conversationHistory, response);
        saveConversationToRedis(conversationId, conversationHistory);

        return response;
    }

    private List<Map<String, Object>> getConversationHistory(String conversationId) {
        List<Map<String, Object>> conversationHistory =
                (List<Map<String, Object>>) redisTemplate.opsForValue().get(conversationId);

        if (conversationHistory == null) {
            conversationHistory = new ArrayList<>();
        }

        return conversationHistory;
    }


    private void addUserMessageToHistory(List<Map<String, Object>> conversationHistory, String prompt) {
        Map<String, Object> userMessage = Map.of(
                "role", "user",
                "content", prompt
        );
        conversationHistory.add(userMessage);
    }

    private void addAssistantMessageToHistory(List<Map<String, Object>> conversationHistory, String response) {
        Map<String, Object> assistantMessage = Map.of(
                "role", "assistant",
                "content", response
        );
        conversationHistory.add(assistantMessage);
    }

    private void saveConversationToRedis(String conversationId, List<Map<String, Object>> conversationHistory) {
        redisTemplate.opsForValue().set(conversationId, conversationHistory);
    }
}