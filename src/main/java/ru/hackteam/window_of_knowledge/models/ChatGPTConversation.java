package ru.hackteam.window_of_knowledge.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ChatGPTConversation {
    private String conversationId;
    private List<Map<String, Object>> context;

    public ChatGPTConversation(String conversationId, List<Map<String, Object>> context) {
        this.conversationId = conversationId;
        this.context = context;
    }
}
