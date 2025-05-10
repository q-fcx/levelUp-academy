package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.ParentDTO;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Service.ParentService;
import com.levelup.levelup_academy.Service.StatisticChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/add-child/{parentId}")
    public ResponseEntity registerChild(@RequestBody @Valid Child child, @PathVariable Integer parentId){
        parentService.addChildToParent(child,parentId);
        return ResponseEntity.status(200).body(new ApiResponse("Child registered"));
    }

    @PutMapping("/update-child/{parentId}, {childId}")
    public ResponseEntity updateChild(@RequestBody @Valid Child child,  @PathVariable Integer parentId, @PathVariable Integer childId){
        parentService.updateChild(child,parentId,childId);
        return ResponseEntity.status(200).body(new ApiResponse("Child updated"));
    }

    @DeleteMapping("/delete-child/{parentId}, {childId}")
    public ResponseEntity deleteChild( @PathVariable Integer parentId, @PathVariable Integer childId){
        parentService.deleteChild(parentId,childId);
        return ResponseEntity.status(200).body(new ApiResponse("Child deleted"));
    }



}
