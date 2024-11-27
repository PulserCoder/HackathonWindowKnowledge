package ru.hackteam.window_of_knowledge.services;

import org.springframework.stereotype.Service;
import ru.hackteam.window_of_knowledge.models.TextData;

@Service
public class TextServiceImpl {
    public String saveTextToBd(TextData textData) {
        return "Ваш текстовый файл добавлен с форматом: " + textData;
    }
}
