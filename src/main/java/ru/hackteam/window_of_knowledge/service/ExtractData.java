package ru.hackteam.window_of_knowledge.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExtractData {
    String saveDataToBd(MultipartFile avatar);
}
