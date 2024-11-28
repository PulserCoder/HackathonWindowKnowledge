package ru.hackteam.window_of_knowledge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hackteam.window_of_knowledge.models.TextData;
import ru.hackteam.window_of_knowledge.services.*;

import java.io.IOException;
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
    public DocxService docxService;

    @Autowired
    public TextFileServiceImpl textFileService;

    @Autowired
    private PdfService pdfService;



    @PostMapping(value = "text-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> saveTexFiletFormat(@RequestParam MultipartFile avatar) throws IOException, InterruptedException {
        return textFileService.saveDataToBd(avatar);
    }

    @PostMapping(path = "text")
    public List<String> saveTextFormat(@RequestBody TextData textData) throws IOException, InterruptedException {
        return textServiceImpl.saveTextToBd(textData);
    }


    @PostMapping(value = "pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> convertPdf(@RequestParam("file") MultipartFile file, @RequestParam(value = "startPage", required = false, defaultValue = "0") Integer startPage,
                                   @RequestParam(value = "endPage", required = false,  defaultValue = "0") Integer endPage) throws IOException, InterruptedException {
        return pdfService.convertPdfToText(file, startPage, endPage);
    }

    @PostMapping(value = "docx-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<String> saveDocxFiletFormat(@RequestParam MultipartFile avatar) throws IOException, InterruptedException {
        return docxService.processDocxFile(avatar);
    }




}