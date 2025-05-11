package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTrainer(@PathVariable Integer id,
                                                @RequestBody @Valid TrainerDTO trainerDTO){
        trainerService.updateTrainer(id, trainerDTO);
        return ResponseEntity.ok("Trainer updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Integer id){
        trainerService.deleteTrainer(id);
        return ResponseEntity.ok("Trainer deleted successfully");
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
    @PutMapping("/{trainerId}/trophy/{playerId}")
    public ResponseEntity<String> giveTrophyToPlayer(@PathVariable Integer trainerId,
                                                     @PathVariable Integer playerId) {
        trainerService.giveTrophyToPlayer(trainerId, playerId);
        return ResponseEntity.ok("Trophy granted to player if eligible.");
    }

    @PutMapping("/{trainerId}/give/{proId}")
    public ResponseEntity<String> giveTrophyToProfessional(@PathVariable Integer trainerId,
                                                           @PathVariable Integer proId) {
        trainerService.giveTrophyToProfessional(trainerId, proId);
        return ResponseEntity.ok("Trophy granted to professional if eligible.");
    }

    @PutMapping("/{trainerId}/give/{childId}")
    public ResponseEntity<String> giveTrophyToChild(@PathVariable Integer trainerId,
                                                    @PathVariable Integer childId) {
        trainerService.giveTrophyToChild(trainerId, childId);
        return ResponseEntity.ok("Trophy granted to child if eligible.");
    }


    @PostMapping("/addStatisticToChild/{trainerId}/{childId}")
    public ResponseEntity addStatisticToChild(@PathVariable Integer trainerId, @PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO statisticChildDTO) {
        trainerService.addStatisticToChild(trainerId, childId, statisticChildDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/addStatisticToPlayer/{trainerId}/{playerId}")
    public ResponseEntity addStatisticToChild(@PathVariable Integer trainerId, @PathVariable Integer playerId, @RequestBody @Valid StatisticPlayerDTO statisticPlayerDTO) {
        trainerService.addStatisticToPlayer(trainerId, playerId, statisticPlayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/addStatisticToPro/{trainerId}/{proId}")
    public ResponseEntity addStatisticToPro(@PathVariable Integer trainerId, @PathVariable Integer proId, @RequestBody @Valid StatisticProDTO statisticProDTO) {
        trainerService.addStatisticToPro(trainerId, proId, statisticProDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }


}
