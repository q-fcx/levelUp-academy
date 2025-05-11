package com.levelup.levelup_academy.Service;

import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public class WhatsAppService{

    private static final String API_URL = "https://api.ultramsg.com/instance119361/messages/chat";  // Replace with your instance URL
    private static final String TOKEN = "iju8dgy6ew2ambgw";  // Replace with your UltraMsg token

    public void sendWhatsAppMessage(String toPhoneNumber, String message) {
        try {
            // Send message using Unirest POST request
            HttpResponse<String> response = (HttpResponse<String>) Unirest.post(API_URL)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", TOKEN)
                    .field("to", toPhoneNumber)  // Add the recipient phone number with country code, e.g., +1234567890
                    .field("body", message)
                    .asString();

            // Print the response from UltraMsg API for debugging purposes
            System.out.println(((kong.unirest.HttpResponse<?>) response).getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to send WhatsApp message: " + e.getMessage());
        }
    }
}
