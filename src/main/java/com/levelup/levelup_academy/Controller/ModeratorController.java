package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Service.ModeratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/moderator")
@RequiredArgsConstructor
public class ModeratorController {
    private final ModeratorService moderatorService;

    //GET
    @GetMapping("/get")
    public ResponseEntity gatAllModerator(){
        return ResponseEntity.status(200).body(moderatorService.getAllModerator());
    }
    @PostMapping("/register")
    public ResponseEntity registerModerator(@RequestBody @Valid ModeratorDTO moderatorDTO){
        moderatorService.registerModerator(moderatorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Moderator registered"));

    }

    // Update moderator
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateModerator(@PathVariable Integer id, @RequestBody @Valid ModeratorDTO moderatorDTO) {
        moderatorService.updateModerator(id, moderatorDTO);
        return ResponseEntity.ok("Moderator updated successfully");
    }

    // Delete moderator
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteModerator(@PathVariable Integer id) {
        moderatorService.deleteModerator(id);
        return ResponseEntity.ok("Moderator deleted successfully");
    }

    @PostMapping("/review-contract/{contractId}/{proId}")
    public ResponseEntity reviewContract(
            @PathVariable Integer contractId,@PathVariable Integer proId) {

        moderatorService.reviewContract(contractId,proId);
        return ResponseEntity.ok("Contract reviewed and Pro has been notified.");
    }


}
