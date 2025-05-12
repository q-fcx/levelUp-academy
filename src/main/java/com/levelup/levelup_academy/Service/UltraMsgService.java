package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;

@Service
public class UltraMsgService {

    public void sendWhatsAppMessage(String to, String message) {
        try {
            // Configure the timeouts
            Unirest.config()
                    .connectTimeout(5000)   // 5 seconds connection timeout
                    .socketTimeout(10000);  // 10 seconds socket timeout

            // Send message via UltraMsg API
            HttpResponse<String> response = Unirest.post("https://api.ultramsg.com/instance118675/messages/chat")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", "zq4aq150ze1voxa0") // Your API token here
                    .field("to", to) // Phone number of the recipient
                    .field("body", message) // The message content
                    .asString(); // Get response as a string

            // Print the response body for debugging
            System.out.println("WhatsApp Response: " + response.getBody());
        } catch (Exception e) {
            // Handle the exception and throw custom exception
            throw new ApiException("Failed to send WhatsApp message: " + e.getMessage());
        }
    }
}