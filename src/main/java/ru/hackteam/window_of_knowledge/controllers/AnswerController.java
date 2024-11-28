package ru.hackteam.window_of_knowledge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hackteam.window_of_knowledge.api_openai.ChatGPTClient;
import ru.hackteam.window_of_knowledge.models.Question;
import ru.hackteam.window_of_knowledge.services.AnswerService;
import ru.hackteam.window_of_knowledge.services.ChatGPTDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.IOException;

@RequestMapping(path = "question")
@RestController
public class AnswerController {

    private final ChatGPTDialog dialog;
    private final AnswerService answerService;

    @Autowired
    public AnswerController(ChatGPTDialog dialog, AnswerService answerService) {
        this.dialog = dialog;
        this.answerService = answerService;
    }

    @PostMapping(path = "/ask/{assistant_id}/{conversation_id}")
    public String answerForUserQuestion(@RequestBody Question question,
                                        @PathVariable("assistant_id") String assistantId,
                                        @PathVariable("conversation_id") String conversationId) throws Exception {
        return answerService.getAnswer(question, assistantId, conversationId);
    }
}
