package ru.hackteam.window_of_knowledge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hackteam.window_of_knowledge.models.Question;
import ru.hackteam.window_of_knowledge.services.AnswerService;

@RequestMapping(path = "question")
@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping(path = "/ask")
    public String answerForUserQuestion(@RequestBody Question question) {
        return answerService.getAnswer(question);
    }
}
