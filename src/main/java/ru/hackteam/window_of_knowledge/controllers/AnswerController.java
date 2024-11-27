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

@RequestMapping(path = "question")
@RestController
public class AnswerController {

    private final ChatGPTDialog dialog;

    @Autowired
    public AnswerController(ChatGPTDialog dialog) {
        this.dialog = dialog;
    }

    @PostMapping(path = "/ask")
    public String answerForUserQuestion(@RequestBody String question) throws Exception {
        return dialog.sendMessage(question, "435");
    }
}
