package ru.hackteam.window_of_knowledge.services;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.api_openai.OpenAIEmbeddingsAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmbeddingService {
    private final OpenAIEmbeddingsAPI openAIEmbeddingsAPI;
    private final RedisTemplate<String, Object> redisTemplate;

    public EmbeddingService(OpenAIEmbeddingsAPI openAIEmbeddingsAPI, RedisTemplate<String, Object> redisTemplate) {
        this.openAIEmbeddingsAPI = openAIEmbeddingsAPI;
        this.redisTemplate = redisTemplate;
    }


    public void processText(String id, String text) throws InterruptedException, IOException {
        double[] embeddings = openAIEmbeddingsAPI.getEmbeddings(text);
        saveToRedis(id, text, embeddings);
    }

    private void saveToRedis(String id, String text, double[] embeddings) {

        Map<String, Object> companyData = (Map<String, Object>) redisTemplate.opsForValue().get(id);
        if (companyData == null) {
            companyData = new HashMap<>();
            companyData.put("id", id);
            companyData.put("texts", new HashMap<String, double[]>());
        }

        Map<String, double[]> texts = (Map<String, double[]>) companyData.get("texts");
        texts.put(text, embeddings);

        redisTemplate.opsForValue().set(id, companyData);
    }

    public Map<String, Object> getCompanyData(String id) {
        return (Map<String, Object>) redisTemplate.opsForValue().get(id);
    }
}

