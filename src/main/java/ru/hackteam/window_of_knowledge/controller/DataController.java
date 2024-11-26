package ru.hackteam.window_of_knowledge.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "data")
public class DataController {

    @PostMapping(path = "excel/{excel-format}")
    public String saveExcelFormat(@PathVariable("excel-format") String excelFormat) {
        return "Ваш excel файл добавлен с форматом: " + excelFormat;
    }

    @PostMapping(path = "notion/{notion-format}")
    public String saveNotionFormat(@PathVariable("notion-format") String notionFormat) {
        return "Ваш notion файл добавлен с форматом: " + notionFormat;
    }

    @PostMapping(path = "text/{text-format}")
    public String saveTextFormat(@PathVariable("text-format") String textFormat) {
        return "Ваш text файл добавлен с форматом: " + textFormat;
    }

    @PostMapping(path = "photo/{photo-format}")
    public String savePhotoFormat(@PathVariable("photo-format") String photoFormat) {
        return "Ваша фотография добавлена с форматом: " + photoFormat;
    }
}



