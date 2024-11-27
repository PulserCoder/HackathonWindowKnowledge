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
    @Autowired
    public ExcelServiceImpl excelServiceImpl;

    @Autowired
    private PdfService pdfService;

    @PostMapping(value = "excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveExcelFormat(@RequestParam MultipartFile avatar, @RequestParam(required = false) String startCell1, @RequestParam(required = false) String startCell2) {
        return excelServiceImpl.saveDataToBd(avatar, startCell1, startCell2);
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


    @PostMapping(value = "pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String convertPdf(@RequestParam("file") MultipartFile file, @RequestParam(value = "startPage", required = false, defaultValue = "0") Integer startPage,
                             @RequestParam(value = "endPage", required = false,  defaultValue = "0") Integer endPage) {
        return pdfService.convertPdfToText(file, startPage, endPage);
    }
}



