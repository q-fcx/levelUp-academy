package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.ParentDTO;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Service.ParentService;
import com.levelup.levelup_academy.Service.StatisticChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;



    @GetMapping("/get")
    public ResponseEntity getAllParents(@AuthenticationPrincipal User moderator) {
        return ResponseEntity.status(200).body(parentService.getAllParents(moderator.getModerator().getId()));
    }

    @PostMapping("/register")
    public ResponseEntity registerParent(@RequestBody @Valid ParentDTO parentDTO){
        parentService.registerParent(parentDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Parent registered"));
    }

    @PutMapping("/edit")
    public ResponseEntity editParent(@AuthenticationPrincipal User parent, @RequestBody ParentDTO parentDTO) {
            parentService.editParent(parent.getId(), parentDTO);
            return ResponseEntity.status(200).body("Parent details updated successfully");

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteParent(@AuthenticationPrincipal User parent) {
        parentService.deleteParent(parent.getId());
        return ResponseEntity.status(200).body("Parent deleted successfully");
    }

    @PostMapping("/add-child")
    public ResponseEntity registerChild(@AuthenticationPrincipal User parent, @RequestBody @Valid Child child){
        parentService.addChildToParent(parent.getParent().getId(),child);
        return ResponseEntity.status(200).body(new ApiResponse("Child registered"));
    }

    @PutMapping("/update-child/{childId}")
    public ResponseEntity updateChild(@AuthenticationPrincipal User parent, @PathVariable Integer childId, @RequestBody @Valid Child child ){
        parentService.updateChild(parent.getId(),childId, child);
        return ResponseEntity.status(200).body(new ApiResponse("Child updated"));
    }

    @DeleteMapping("/delete-child/{childId}")
    public ResponseEntity deleteChild(@AuthenticationPrincipal User parent, @PathVariable Integer childId){
        parentService.deleteChild(parent.getId(),childId);
        return ResponseEntity.status(200).body(new ApiResponse("Child deleted"));
    }

    @GetMapping("/child-statistic/{childId}")
    public ResponseEntity getChildStatistic(@AuthenticationPrincipal User parent,@PathVariable Integer childId) {
        return ResponseEntity.status(200).body(parentService.getChildStatistic(parent.getParent().getId(), childId));
    }

    @GetMapping("/get-games/{childId}")
    public ResponseEntity getGamesByChildAge(@AuthenticationPrincipal User parent, @PathVariable Integer childId) {
        return ResponseEntity.status(200).body(parentService.getGamesByChildAge(parent.getId(), childId));
    }

    @GetMapping("/get-child-stati-by-parent/{childId}")
    public ResponseEntity<StatisticChild> getChildStatisticsByParent(@AuthenticationPrincipal User parentId, @PathVariable Integer childId) {
        return ResponseEntity.ok(parentService.getMyChildStatisticsByChildId(parentId.getId(), childId));
    }




}
