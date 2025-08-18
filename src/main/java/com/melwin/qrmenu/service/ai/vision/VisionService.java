package com.melwin.qrmenu.service.ai.vision;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.melwin.qrmenu.service.ai.gemini.GeminiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class VisionService {

    private final GeminiService geminiService;

    public Map<String, Object>   extractTextFromMultipleImages(MultipartFile[] multipartFile) throws IOException {
        List<String> result = new ArrayList<>();

        for (MultipartFile file : multipartFile) {
            String text = extractTextFromImage(file);
            result.add(text);
        }

        for (String text : result) {
            System.out.println(text);
        }

        String extractedText = String.join("\n", result);
        return geminiService.getStructredMenu(extractedText);
    }

    public String extractTextFromImage(MultipartFile file) throws IOException {
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat =  Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();

        AnnotateImageRequest  request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
        try(ImageAnnotatorClient imageAnnotatorClient = ImageAnnotatorClient.create()) {
            AnnotateImageResponse response = imageAnnotatorClient
                    .batchAnnotateImages(List.of(request))
                    .getResponses(0);
            if (response.hasError()) {
                throw new IOException(response.getError().getMessage());
            }
            return response.getFullTextAnnotation().getText();
        }

    }
}
