package ru.hackteam.window_of_knowledge.services;

import org.springframework.web.multipart.MultipartFile;

public interface ExtractData {
    String saveDataToBd(MultipartFile avatar);

//    String saveDataToBd(MultipartFile avatar, String startCell, String endCell);
}
