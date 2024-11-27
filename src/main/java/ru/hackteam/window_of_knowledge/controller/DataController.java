package ru.hackteam.window_of_knowledge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hackteam.window_of_knowledge.model.TextData;
import ru.hackteam.window_of_knowledge.service.ExtractData;
import ru.hackteam.window_of_knowledge.service.TextServiceImpl;

@RestController
@RequestMapping(path = "data")
public class DataController {
    @Autowired
    private ExtractData excelServiceImpl;
    @Autowired
    private ExtractData notionServiceImpl;
    @Autowired
    private ExtractData textFileServiceImpl;
    @Autowired
    public TextServiceImpl textServiceImpl;


    @PostMapping(value = "excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveExcelFormat(@RequestParam MultipartFile avatar) {
        return excelServiceImpl.saveDataToBd(avatar);
    }

    @PostMapping(value = "notion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveNotionFormat(@RequestParam MultipartFile avatar) {
        return notionServiceImpl.saveDataToBd(avatar);
    }

    @PostMapping(value = "text-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveTexFiletFormat(@RequestParam MultipartFile avatar) {
        return textFileServiceImpl.saveDataToBd(avatar);
    }

    @PostMapping(path = "text")
    public String saveTextFormat(@RequestBody TextData textData) {
        return textServiceImpl.saveTextToBd(textData);
    }

}



