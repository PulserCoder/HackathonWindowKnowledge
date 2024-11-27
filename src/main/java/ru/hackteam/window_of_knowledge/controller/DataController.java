package ru.hackteam.window_of_knowledge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hackteam.window_of_knowledge.models.TextData;
import ru.hackteam.window_of_knowledge.services.*;

import java.util.List;

@RestController
@RequestMapping(path = "data")
public class DataController {

    private ExtractData extractData;
    @Autowired
    public TextServiceImpl textServiceImpl;

    @PostMapping(value = "excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveExcelFormat(@RequestParam MultipartFile avatar) {
        extractData = new ExcelServiceImpl();
        return extractData.saveDataToBd(avatar);
    }

    @PostMapping(value = "notion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveNotionFormat(@RequestParam MultipartFile avatar) {
        extractData = new NotionServiceImpl();
        return extractData.saveDataToBd(avatar);
    }

    @PostMapping(value = "text-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveTexFiletFormat(@RequestParam MultipartFile avatar) {
        extractData = new TextFileServiceImpl();
        return extractData.saveDataToBd(avatar);
    }

    @PostMapping(path = "text")
    public List<String> saveTextFormat(@RequestBody TextData textData) {
        return textServiceImpl.saveTextToBd(textData);
    }

}



