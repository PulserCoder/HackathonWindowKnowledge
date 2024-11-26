package ru.hackteam.window_of_knowledge.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hackteam.window_of_knowledge.service.ExcelService;
import ru.hackteam.window_of_knowledge.service.NotionService;
import ru.hackteam.window_of_knowledge.service.TextService;

@RestController
@RequestMapping(path = "data")
public class DataController {

    private ExcelService excelService;
    private NotionService notionService;
    private TextService textService;


    @PostMapping(value = "excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveExcelFormat(@RequestParam MultipartFile avatar) {
        return excelService.saveBuddingToBd(avatar);
    }

    @PostMapping(value = "notion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveNotionFormat(@RequestParam MultipartFile avatar) {
        return notionService.saveBuddingToBd(avatar);
    }

    @PostMapping(value = "text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveTextFormat(@RequestParam MultipartFile avatar) {
        return textService.saveBuddingToBd(avatar);
    }

}



