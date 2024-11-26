package ru.hackteam.window_of_knowledge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class NotionService implements ExtractDataImpl {
    public String saveBuddingToBd(MultipartFile avatar) {
        return "Ваш notion файл добавлен с форматом: " + avatar;
    }
}
