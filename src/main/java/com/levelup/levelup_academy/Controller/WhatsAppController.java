package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.WhatsAppDTO;
import com.levelup.levelup_academy.Service.UltraMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/whatsapp")
public class WhatsAppController {

    private final UltraMsgService ultraMsgService;

    @Autowired
    public WhatsAppController(UltraMsgService ultraMsgService) {
        this.ultraMsgService = ultraMsgService;
    }

    // Endpoint to send a WhatsApp message
    @PostMapping("/send")
    public ResponseEntity sendTestMessage(@RequestBody WhatsAppDTO messageRequest) {
        ultraMsgService.sendWhatsAppMessage(messageRequest.getTo(), messageRequest.getMessage());
        return ResponseEntity.status(200).body("Message sent to: " + messageRequest.getTo());
    }
}