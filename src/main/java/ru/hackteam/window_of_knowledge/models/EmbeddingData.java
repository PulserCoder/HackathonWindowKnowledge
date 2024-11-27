package ru.hackteam.window_of_knowledge.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
public class EmbeddingData {
    private String text;
    private List<Double> embedding;
}
