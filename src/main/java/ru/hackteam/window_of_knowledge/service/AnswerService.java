package ru.hackteam.window_of_knowledge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hackteam.window_of_knowledge.model.Question;
@Service
public class AnswerService {

    public String getAnswer(Question question) {
        return "Вот наш ответ";
    }
}
