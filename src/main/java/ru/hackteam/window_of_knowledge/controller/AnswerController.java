package ru.hackteam.window_of_knowledge.controller;

import org.springframework.web.bind.annotation.*;
import ru.hackteam.window_of_knowledge.model.Question;

@RequestMapping(path = "question")
@RestController
public class AnswerController {

    @PostMapping(path = "/ask")
    public String answerForUserQuestion(@RequestBody Question question) {
        return "Вот наш ответ на вопрос: " + question.getGuestion();
    }

}
