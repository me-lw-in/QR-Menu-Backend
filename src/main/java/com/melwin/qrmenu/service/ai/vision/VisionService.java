package com.melwin.qrmenu.service.ai.vision;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.melwin.qrmenu.service.ai.gemini.GeminiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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
        System.out.println("mel");
        AnnotateImageRequest  request = AnnotateImageRequest.newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
        System.out.println("annote");

        String json = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (json == null || json.isBlank()) {
            throw new IOException("Google Cloud Vision API key not set in environment variable GOOGLE_APPLICATION_CREDENTIALS_JSON");
        }

        System.out.println("here0");
        File tempFile = File.createTempFile("gcp-key", ".json");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(json.getBytes());
        }
        System.out.println("here");

        GoogleCredentials credentials = GoogleCredentials.fromStream(Files.newInputStream(tempFile.toPath()));
        System.out.println("here2");
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();
        System.out.println("ere");

        try(ImageAnnotatorClient imageAnnotatorClient = ImageAnnotatorClient.create(settings)) {
            AnnotateImageResponse response = imageAnnotatorClient
                    .batchAnnotateImages(List.of(request))
                    .getResponses(0);
            System.out.println("res");
            if (response.hasError()) {
                System.out.println("error");
                throw new IOException(response.getError().getMessage());
            }
            return response.getFullTextAnnotation().getText();
        }

    }
}
