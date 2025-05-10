package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.ParentDTO;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.Service.ParentService;
import com.levelup.levelup_academy.Service.StatisticChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final StatisticChildService statisticChildService;


    @GetMapping("/get")
    public ResponseEntity getAllParents() {
        return ResponseEntity.status(200).body(parentService.getAllParents());
    }

    @PostMapping("/register")
    public ResponseEntity registerParent(@RequestBody @Valid ParentDTO parentDTO){
        parentService.registerParent(parentDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Parent registered"));
    }

    @PutMapping("/edit/{parentId}")
    public ResponseEntity editParent(@PathVariable Integer parentId, @RequestBody ParentDTO parentDTO) {
            parentService.editParent(parentId, parentDTO);
            return ResponseEntity.status(200).body("Parent details updated successfully");

    }

    @DeleteMapping("/delete/{parentId}")
    public ResponseEntity deleteParent(@PathVariable Integer parentId) {
        parentService.deleteParent(parentId);
        return ResponseEntity.status(200).body("Parent deleted successfully");
    }
    @GetMapping("/top")
    public ResponseEntity getTopChild() {
        return ResponseEntity.ok(statisticChildService.getTopChildByTrophy());
    }

    @GetMapping("/top5/{game}")
    public ResponseEntity getTop5Children(@PathVariable String game) {
        return ResponseEntity.ok(statisticChildService.getTop5ChildrenByGame(game));
    }

}
