package com.melwin.qrmenu.controller;

import com.melwin.qrmenu.dto.menu.create.MenuSetupRequestDto;
import com.melwin.qrmenu.dto.menu.view.MenuResponseDto;
import com.melwin.qrmenu.service.ai.vision.VisionService;
import com.melwin.qrmenu.service.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("api/menu")
public class MenuController {

    private final VisionService visionService;
    private final MenuService menuService;

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

    @GetMapping("/my-menu/{restoId}")
    public ResponseEntity<MenuResponseDto> getMenuByRestoId(@PathVariable Long restoId){
        MenuResponseDto menu =  menuService.getMenu(restoId);
        if (menu == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(menu);
        }
    }

    @PostMapping("/create-menu")
    public ResponseEntity<String> saveMenu(@RequestBody MenuSetupRequestDto menu){
        menuService.createMenu(menu);
        return ResponseEntity.status(HttpStatus.OK).body("Menu created!");
    }
}
