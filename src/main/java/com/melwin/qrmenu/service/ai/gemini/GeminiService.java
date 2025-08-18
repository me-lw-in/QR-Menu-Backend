package com.melwin.qrmenu.service.ai.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestTemplate restTemplate = new  RestTemplate();
//    @Value("${gemini.api.key}")
//    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Map<String,Object> getStructredMenu(String extractedText) throws JsonProcessingException {

        String prompt = """
        You are a helpful assistant that structures messy menu text into a clean Java-compatible JSON format.

        Output JSON that matches this Java DTO structure:
        {
          "categories": [
            {
              "categoryName": "string",
              "items": [
                {
                  "name": "string",
                  "price": number,
                  "isVeg": true/false
                }
              ]
            }
          ]
        }

        Infer `isVeg` if possible based on dish name (e.g. contains 'Paneer', 'Veg', 'Manchurian' → true).

        Now here is the extracted menu:
        """ + extractedText;


        // The client gets the API key from the environment variable `GEMINI_API_KEY`.
        Client client = new Client();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null);
        String rawResponseText = response.text();
        System.out.println("Raw text: " + rawResponseText);
        int startIndex = rawResponseText.indexOf('{');
        int endIndex = rawResponseText.lastIndexOf('}');
        if (startIndex == -1 || endIndex == -1) {
            throw new RuntimeException("Invalid JSON response");
        }
        String cleantext = rawResponseText.substring(startIndex, endIndex+1);
        System.out.println("Cleaned text: " + cleantext);

        return objectMapper.readValue(cleantext, Map.class);
    }
}
