package ru.hackteam.window_of_knowledge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hackteam.window_of_knowledge.model.TextData;

@Service
public class TextServiceImpl {
    public String saveTextToBd(TextData textData) {
        return "Ваш текстовый файл добавлен с форматом: " + textData;
    }
}
