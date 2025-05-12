package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/whatsapp")
public class WhatsAppController {
    private final WhatsAppService whatsAppService;

    // Endpoint to send WhatsApp messages
    @PostMapping("/send")
    public String sendMessage(@RequestParam String toPhoneNumber, @RequestParam String message) {
        whatsAppService.sendWhatsAppMessage(toPhoneNumber, message);
        return "Message sent successfully!";
    }
}
