package ru.hackteam.window_of_knowledge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "answer")
@RestController
public class AnswerController {
    @GetMapping(path = "{question}")
    public String answerForUserQuestion(@PathVariable String question) {
        return "Вот наш ответ";
    }
}
