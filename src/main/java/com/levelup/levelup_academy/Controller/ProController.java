package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Service.ProService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/pro")
@RequiredArgsConstructor
public class ProController {
    private final ProService proService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllPro(){
        return ResponseEntity.status(200).body(proService.getAllPro());
    }
    //Register pro player
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTrainer(
            @RequestPart("pro") ProDTO proDTO,
            @RequestPart("file") MultipartFile file) {
        proService.registerPro(proDTO ,file);
        return ResponseEntity.ok("pro player registered successfully with CV uploaded");
    }
}
