package com.melwin.qrmenu.controller;

import com.melwin.qrmenu.service.ai.vision.VisionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/menu")
public class MenuController {

    private final VisionService visionService;

    @PostMapping("/generate-menu")
    public ResponseEntity<Map<String,Object>> extractMenuByAi(
            @RequestParam("files") MultipartFile[] files) {
        try {
            Map<String, Object> structuredMenu = visionService.extractTextFromMultipleImages(files);
            return ResponseEntity.status(HttpStatus.OK).body(structuredMenu);
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
