package ru.hackteam.window_of_knowledge.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelServiceImpl implements ExtractData {
    public String saveDataToBd(MultipartFile avatar) {
        return "Ваш excel файл добавлен с форматом: " + avatar;
    }
}
