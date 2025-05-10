package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.ProService;
import com.levelup.levelup_academy.Service.StatisticProService;
import jakarta.validation.Valid;
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
    private final StatisticProService statisticProService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllPro() {
        return ResponseEntity.status(200).body(proService.getAllPro());
    }

    //Register pro player
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTrainer(
            @RequestPart("pro") ProDTO proDTO,
            @RequestPart("file") MultipartFile file) {
        proService.registerPro(proDTO, file);
        return ResponseEntity.ok("pro player registered successfully with CV uploaded");
    }

    //Edit
    @PutMapping("/edit/{proId}")
    public ResponseEntity EditProAccount(@PathVariable Integer proId, @RequestBody @Valid ProDTO proDTO) {
        proService.edit(proId, proDTO);
        return ResponseEntity.ok("Pro player information updated successfully");
    }

    // Endpoint to delete Pro player by ID
    @DeleteMapping("/delete/{proId}")
    public ResponseEntity deleteProPlayer(@PathVariable Integer proId) {
        proService.delete(proId);
        return ResponseEntity.ok("Your account have been deleted successfully.");

    }

    @PostMapping("/approve/{adminId}/{proId}")
    public ResponseEntity<String> approvePro(@PathVariable Integer adminId, @PathVariable Integer proId) {
        proService.approveProByAdmin(adminId, proId);
        return ResponseEntity.ok("The professional player has been approved.");
    }
    @PutMapping("/reject/{adminId}/{proId}")
    public ResponseEntity<String> rejectPro(@PathVariable Integer adminId, @PathVariable Integer proId) {
        proService.rejectProByAdmin(adminId, proId);
        return ResponseEntity.ok("The professional player has been rejected and deleted.");
    }


    @GetMapping("/top")
    public ResponseEntity getTopPro() {
        return ResponseEntity.ok(statisticProService.getTopProByTrophy());
    }

    @GetMapping("/top5/{game}")
    public ResponseEntity getTop5Pros(@PathVariable String game) {
        return ResponseEntity.ok(statisticProService.getTop5ProsByGame(game));
    }


}