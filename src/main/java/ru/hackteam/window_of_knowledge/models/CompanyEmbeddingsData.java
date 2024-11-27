package ru.hackteam.window_of_knowledge.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CompanyEmbeddingsData {
    private String id;
    private List<EmbeddingData> texts;
}
