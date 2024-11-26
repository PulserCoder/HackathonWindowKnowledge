package ru.hackteam.window_of_knowledge.model;

public class Question {
    private String guestion; // Замените на 'question', если хотите

    public String getGuestion() {
        return guestion;
    }

    public void setGuestion(String guestion) {
        this.guestion = guestion;
    }

    public Question() {
        // Конструктор по умолчанию
    }

    public Question(String question) {
        this.guestion = question;
    }
}
