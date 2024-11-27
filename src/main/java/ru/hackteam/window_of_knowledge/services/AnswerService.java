package ru.hackteam.window_of_knowledge.services;

import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.models.Question;
@Service
public class AnswerService {

    public String getAnswer(Question question) {
        return "Вот наш ответ";
    }
}
