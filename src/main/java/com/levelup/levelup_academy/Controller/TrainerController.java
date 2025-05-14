package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Service.SessionService;
import com.levelup.levelup_academy.Service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final SessionService sessionService;
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity registerTrainer(
            @RequestPart("trainer") TrainerDTO trainerDTO,
            @RequestPart("file") MultipartFile file) {
        trainerService.registerTrainer(trainerDTO, file);
        return ResponseEntity.status(200).body(new ApiResponse("Trainer registered successfully with CV uploaded"));
    }

    @GetMapping("/get")
    public ResponseEntity getAllTrainers(@AuthenticationPrincipal User moderator) {
       return ResponseEntity.status(200).body(trainerService.getAllTrainers(moderator.getModerator().getId()));
    }
    @PutMapping("/edit")
    public ResponseEntity updateTrainer(@AuthenticationPrincipal User trainerId,
                                                @RequestBody @Valid TrainerDTO trainerDTO){
        trainerService.updateTrainer(trainerId.getTrainer().getId(), trainerDTO);
        return ResponseEntity.ok(new ApiResponse("Trainer updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTrainer(@AuthenticationPrincipal User trainerId){
        trainerService.deleteTrainer(trainerId.getTrainer().getId());
        return ResponseEntity.ok(new ApiResponse("Trainer deleted successfully"));
    }



    @GetMapping("/cv/{trainerId}")
    public ResponseEntity<byte[]> downloadTrainerCv(@AuthenticationPrincipal User admin, @PathVariable Integer trainerId) {
        byte[] cvContent = trainerService.downloadTrainerCv(admin.getId(), trainerId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("trainer_cv_" + trainerId + ".pdf")
                .build());

        return new ResponseEntity<>(cvContent, headers, HttpStatus.OK);
    }

    @GetMapping("/get-players/{sessionId}")
    public ResponseEntity getAllPlayersInSession(@AuthenticationPrincipal User trainer, @PathVariable Integer sessionId) {
        return ResponseEntity.status(200).body(trainerService.getAllPlayersInSession(trainer.getTrainer().getId(), sessionId));
    }
    @PutMapping("/give-player/{playerId}")
    public ResponseEntity giveTrophyToPlayer(@AuthenticationPrincipal User trainerId, @PathVariable Integer playerId) {
        trainerService.giveTrophyToPlayer(trainerId.getTrainer().getId(), playerId);
        return ResponseEntity.ok(new ApiResponse("Trophy granted to player if eligible."));
    }

    @PutMapping("/give-pro/{proId}")
    public ResponseEntity giveTrophyToProfessional(@AuthenticationPrincipal User trainer, @PathVariable Integer proId) {
        trainerService.giveTrophyToPro(trainer.getTrainer().getId(), proId);
        return ResponseEntity.ok(new ApiResponse("Trophy granted to professional ."));
    }

    @PutMapping("/give-child/{childId}")
    public ResponseEntity giveTrophyToChild(@AuthenticationPrincipal User trainerId,@PathVariable Integer childId) {
        trainerService.giveTrophyToChild(trainerId.getId(), childId);
        return ResponseEntity.ok(new ApiResponse("Trophy granted to child if eligible."));
    }


    @PostMapping("/addStatisticToChild/{childId}")
    public ResponseEntity addStatisticToChild(@AuthenticationPrincipal User trainerId, @PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO statisticChildDTO) {
        trainerService.addStatisticToChild(trainerId.getTrainer().getId(), childId, statisticChildDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/addStatisticToPlayer/{playerId}")
    public ResponseEntity addStatisticToChild(@AuthenticationPrincipal User trainerId, @PathVariable Integer playerId, @RequestBody @Valid StatisticPlayerDTO statisticPlayerDTO) {
        trainerService.addStatisticToPlayer(trainerId.getTrainer().getId(), playerId, statisticPlayerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/addStatisticToPro/{proId}")
    public ResponseEntity addStatisticToPro(@AuthenticationPrincipal User trainerId, @PathVariable Integer proId, @RequestBody @Valid StatisticProDTO statisticProDTO) {
        trainerService.addStatisticToPro(trainerId.getTrainer().getId(), proId, statisticProDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Statistic added successfully."));
    }

    @PostMapping("/send-promotion-request/{playerId}")
    public ResponseEntity sendPromotionRequestToModerator(@AuthenticationPrincipal User trainerId, @PathVariable Integer playerId) {
        trainerService.sendPromotionRequestToModerator(trainerId.getId(),playerId);
        return ResponseEntity.ok(new ApiResponse("Promotion request sent to Moderator successfully."));
    }



    @PostMapping("/approve-trainer/{trainerId}")
    public ResponseEntity<String> approvePro(@AuthenticationPrincipal User adminId, @PathVariable Integer trainerId) {
        trainerService.approveTrainerByAdmin(adminId.getId(), trainerId);
        return ResponseEntity.ok("The trainer has been approved.");
    }
    @PutMapping("/reject-trainer/{trainerId}")
    public ResponseEntity<String> rejectPro(@AuthenticationPrincipal User adminId, @PathVariable Integer trainerId) {
        trainerService.rejectTrainerByAdmin(adminId.getId(), trainerId);
        return ResponseEntity.ok("The trainer has been rejected and deleted.");
    }

}
