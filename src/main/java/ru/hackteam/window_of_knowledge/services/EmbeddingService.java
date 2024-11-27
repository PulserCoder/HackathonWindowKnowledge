package ru.hackteam.window_of_knowledge.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.api_openai.OpenAIEmbeddingsAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EmbeddingService {
    private final OpenAIEmbeddingsAPI openAIEmbeddingsAPI;
    private final RedisTemplate<String, Object> redisTemplate;

    public EmbeddingService(OpenAIEmbeddingsAPI openAIEmbeddingsAPI,
                            @Qualifier("redisTemplateForEmbeddings") RedisTemplate<String, Object> redisTemplate) {
        this.openAIEmbeddingsAPI = openAIEmbeddingsAPI;
        this.redisTemplate = redisTemplate;
    }


    public void processText(String id, String text) throws InterruptedException, IOException {
        double[] embeddings = openAIEmbeddingsAPI.getEmbeddings(text);
        saveToRedis(id, text, embeddings);
    }

    private void saveToRedis(String id, String text, double[] embeddings) {
        Map<String, Object> embeddingData = new HashMap<>();
        embeddingData.put("text", text);
        embeddingData.put("vector", embeddings);

        redisTemplate.opsForList().rightPush(id, embeddingData);
    }

    public List<Map<String, Object>> getEmbeddings(String id) {
        List<Object> rawList = redisTemplate.opsForList().range(id, 0, -1);
        return rawList != null ? rawList.stream()
                .map(obj -> (Map<String, Object>) obj)
                .toList() : List.of();
    }

    private double cosineSimilarity(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must have the same dimensions");
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        normA = Math.sqrt(normA);
        normB = Math.sqrt(normB);

        return dotProduct / (normA * normB);
    }

    public List<Map<String, Object>> findTopRelevantTexts(String id, String text) throws IOException, InterruptedException {
        double[] queryVector = openAIEmbeddingsAPI.getEmbeddings(text);
        List<Map<String, Object>> embeddingsList = getEmbeddings(id);
        List<Map<String, Object>> scoredTexts = embeddingsList.stream()
                .map(entry -> {
                    double[] storedVector = (double[]) entry.get("vector");
                    double similarity = cosineSimilarity(queryVector, storedVector);
                    entry.put("similarity", similarity);
                    return entry;
                })
                .sorted((a, b) -> Double.compare((double) b.get("similarity"), (double) a.get("similarity")))
                .collect(Collectors.toList());

        return scoredTexts.stream().limit(5).toList();
    }

}
