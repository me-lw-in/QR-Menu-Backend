package com.melwin.qrmenu.controller;

import com.melwin.qrmenu.service.menu.QrService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/qrcode")
public class QrCodeController {

    private final QrService qrService;

    @GetMapping(value = "/generate/{restoId}" , produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(@PathVariable Long restoId){
        System.out.println("Generating QR code for restoId: " + restoId);
        try{
            byte[] qrCodeByte = qrService.getQrCode(restoId);
            return ResponseEntity.status(HttpStatus.OK).body(qrCodeByte);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
