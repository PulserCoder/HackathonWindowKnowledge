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

    @Autowired
    public ChatGPTDialog(
            @Qualifier("saveConversation") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getRelevantAnswer(String question, List<String> answers) {
        StringBuilder result = new StringBuilder();

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
                userMessage.put("content", "Текст: " + "\n" +  answer);
                conversationHistory.add(userMessage);

                // Отправляем запрос в GPT
                String gptResponse = ChatGPTClient.sendRequest(conversationHistory);

                // Добавляем ответ GPT к результатам
                if (gptResponse.contains("+")) {
                    result.append(" ");
                    result.append(gptResponse);
                }

            } catch (Exception e) {
                e.printStackTrace();
                result.append("E"); // Добавляем 'E' в случае ошибки
            }
        }

        return result.toString();
    }

    public String sendMessage(String prompt, String conversationId) throws Exception {
        List<Map<String, Object>> conversationHistory = getConversationHistory(conversationId);

        addUserMessageToHistory(conversationHistory, prompt);
        String response = ChatGPTClient.sendRequest(conversationHistory);
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