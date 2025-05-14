package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.ModeratorService;
import com.levelup.levelup_academy.Service.ProService;
import com.levelup.levelup_academy.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/moderator")
@RequiredArgsConstructor
public class ModeratorController {
    private final ModeratorService moderatorService;
    private final ProService proService;
    private final UserService userService;

    //GET
    @GetMapping("/get")
    public ResponseEntity gatAllModerator(@AuthenticationPrincipal User admin){
        return ResponseEntity.status(200).body(moderatorService.getAllModerator(admin.getId()));
    }
    @PostMapping("/register")
    public ResponseEntity registerModerator(@AuthenticationPrincipal User admin,@RequestBody @Valid ModeratorDTO moderatorDTO){
        userService.registerModerator(admin.getId(), moderatorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Moderator registered"));

    }

    // Update moderator
    @PutMapping("/edit")
    public ResponseEntity<String> updateModerator(@AuthenticationPrincipal Moderator moderator, @RequestBody @Valid ModeratorDTO moderatorDTO) {
        moderatorService.updateModerator(moderator.getId(), moderatorDTO);
        return ResponseEntity.ok("Moderator updated successfully");
    }

    // Delete moderator
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteModerator(@AuthenticationPrincipal User moderator) {
        moderatorService.deleteModerator(moderator.getId());
        return ResponseEntity.ok("Moderator deleted successfully");
    }

    @PostMapping("/review-contract/{contractId}/{proId}")
    public ResponseEntity reviewContract(@AuthenticationPrincipal User moderator, @PathVariable Integer contractId, @PathVariable Integer proId) {

        moderatorService.reviewContract(moderator.getModerator().getId(),contractId,proId);
        return ResponseEntity.ok("Contract reviewed and Pro has been notified.");
    }


    @GetMapping("/get-all-pro")
    public ResponseEntity getAllProRequests(@AuthenticationPrincipal Moderator moderator){
        return ResponseEntity.status(200).body(proService.getAllProRequests(moderator.getId()));
    }


    @PostMapping("/send-exam/{proId}")
    public ResponseEntity<String> sendDiscordExam(@AuthenticationPrincipal User moderator,@PathVariable Integer proId) {
        proService.sendDiscordExamLink(moderator.getModerator().getId(),proId);
        return ResponseEntity.status(200).body("Discord exam link has been sent to the Pro.");
    }
    @PostMapping("/send-report/{parentId}")
    public ResponseEntity sendReport(@PathVariable Integer parentId, @RequestBody String reportMessage) {
        moderatorService.sendReportToParent(parentId, reportMessage);
        return ResponseEntity.status(200).body(new ApiResponse("Report sent to parent successfully."));
    }

}
