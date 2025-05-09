package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Service.TrainerService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTrainer(
            @RequestPart("trainer") TrainerDTO trainerDTO,
            @RequestPart("file") MultipartFile file) {
        trainerService.registerTrainer(trainerDTO, file);
        return ResponseEntity.ok("Trainer registered successfully with CV uploaded");
    }

    @GetMapping("/get")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<byte[]> downloadTrainerCv(@PathVariable Integer id) {
        byte[] cvContent = trainerService.downloadTrainerCv(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("trainer_cv_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(cvContent, headers, HttpStatus.OK);
    }

}
