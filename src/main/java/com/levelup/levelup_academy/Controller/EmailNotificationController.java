package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.Service.EmailNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailNotificationController {


    private final EmailNotificationService emailNotificationService;

    @PostMapping("/send")
    public ResponseEntity sendEmail(@Valid @RequestBody EmailRequest emailRequest){
        emailNotificationService.sendEmail(emailRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Email sent successfully"));
    }

}
