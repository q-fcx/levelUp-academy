package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Service.TrainerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PutMapping("/edit")
    public ResponseEntity updateTrainer(@AuthenticationPrincipal User trainerId,
                                                @RequestBody @Valid TrainerDTO trainerDTO){
        trainerService.updateTrainer(trainerId.getId(), trainerDTO);
        return ResponseEntity.ok("Trainer updated successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTrainer(@AuthenticationPrincipal User trainerId){
        trainerService.deleteTrainer(trainerId.getId());
        return ResponseEntity.ok("Trainer deleted successfully");
    }



    @GetMapping("/cv")
    public ResponseEntity<byte[]> downloadTrainerCv(@AuthenticationPrincipal User trainerId) {
        byte[] cvContent = trainerService.downloadTrainerCv(trainerId.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("trainer_cv_" + trainerId.getId() + ".pdf")
                .build());

        return new ResponseEntity<>(cvContent, headers, HttpStatus.OK);
    }
    @PutMapping("/trophy/{playerId}")
    public ResponseEntity giveTrophyToPlayer(@AuthenticationPrincipal User trainerId, @PathVariable Integer playerId) {
        trainerService.giveTrophyToPlayer(trainerId.getId(), playerId);
        return ResponseEntity.ok("Trophy granted to player if eligible.");
    }

    @PutMapping("/give/{proId}")
    public ResponseEntity<String> giveTrophyToProfessional(@AuthenticationPrincipal User trainerId, @PathVariable Integer proId) {
        trainerService.giveTrophyToProfessional(trainerId.getId(), proId);
        return ResponseEntity.ok("Trophy granted to professional if eligible.");
    }

    @PutMapping("/give/{childId}")
    public ResponseEntity<String> giveTrophyToChild(@AuthenticationPrincipal User trainerId,@PathVariable Integer childId) {
        trainerService.giveTrophyToChild(trainerId.getId(), childId);
        return ResponseEntity.ok("Trophy granted to child if eligible.");
    }


    @PostMapping("/addStatisticToChild/{childId}")
    public ResponseEntity addStatisticToChild(@AuthenticationPrincipal User trainerId, @PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO statisticChildDTO) {
        trainerService.addStatisticToChild(trainerId.getId(), childId, statisticChildDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/addStatisticToPlayer/{playerId}")
    public ResponseEntity addStatisticToChild(@AuthenticationPrincipal User trainerId, @PathVariable Integer playerId, @RequestBody @Valid StatisticPlayerDTO statisticPlayerDTO) {
        trainerService.addStatisticToPlayer(trainerId.getId(), playerId, statisticPlayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/addStatisticToPro/{proId}")
    public ResponseEntity addStatisticToPro(@AuthenticationPrincipal User trainerId, @PathVariable Integer proId, @RequestBody @Valid StatisticProDTO statisticProDTO) {
        trainerService.addStatisticToPro(trainerId.getId(), proId, statisticProDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }



    @PostMapping("/approve/{trainerId}")
    public ResponseEntity<String> approvePro(@AuthenticationPrincipal User adminId, @PathVariable Integer trainerId) {
        trainerService.approveTrainerByAdmin(adminId.getId(), trainerId);
        return ResponseEntity.ok("The trainer has been approved.");
    }
    @PutMapping("/reject/{trainerId}")
    public ResponseEntity<String> rejectPro(@PathVariable User adminId, @PathVariable Integer trainerId) {
        trainerService.rejectTrainerByAdmin(adminId.getId(), trainerId);
        return ResponseEntity.ok("The trainer has been rejected and deleted.");
    }

}
