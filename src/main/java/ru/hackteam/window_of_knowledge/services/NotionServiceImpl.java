package ru.hackteam.window_of_knowledge.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class NotionServiceImpl implements ExtractData {
    public String saveDataToBd(MultipartFile avatar) {
        return "Ваш notion файл добавлен с форматом: " + avatar;
    }

}
