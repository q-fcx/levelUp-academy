package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.StatisticChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/child-statistic")
@RequiredArgsConstructor
public class StatisticChildController {
    private final StatisticChildService statisticChildService;

    @PostMapping("/create/{childId}")
    public ResponseEntity createStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.createStatisticChild(trainerId.getId(), childId, dto);
        return ResponseEntity.status(201).body(new ApiResponse("Child statistic created"));
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity updateStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.updateStatistic(trainerId.getId(),statId, dto);
        return ResponseEntity.ok(new ApiResponse("Child statistic updated"));
    }


    @GetMapping("/get-child-stati-by-trainer/{childId}")
    public ResponseEntity<StatisticChild> getChildStatisticsByTrainer(@AuthenticationPrincipal User trainerId, @PathVariable Integer childId) {
        return ResponseEntity.ok(statisticChildService.getStatisticsByChildId(trainerId.getId(), childId));
    }

//    @GetMapping("/by-trainer")
//    public ResponseEntity<List<StatisticChild>> getAllStatsByTrainer(@AuthenticationPrincipal User trainerId) {
//        return ResponseEntity.ok(statisticChildService.getAllStatisticsByTrainerId(trainerId.getId()));
//    }

    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId) {
        statisticChildService.deleteStatistic(trainerId.getId(),statId);
        return ResponseEntity.ok(new ApiResponse("Child statistic deleted"));
    }
    @GetMapping("/top-child-by-rating")
    public ResponseEntity<String> getTopChildByRating(@AuthenticationPrincipal User trainerId) {
        return ResponseEntity.ok(statisticChildService.getTopChildByRating(trainerId.getId()));
    }

//    @GetMapping("/top5")
//    public ResponseEntity<List<StatisticChild>> getTop5ByWinGame(@AuthenticationPrincipal User trainer,@RequestParam Integer winGame) {
//        List<StatisticChild> top5 = statisticChildService.getTop5ChildrenByGame(trainer.getId(),winGame);
//        return ResponseEntity.ok(top5);
//    }
    @PutMapping("/add-win/{statId}")
    public ResponseEntity addWinToChild(@AuthenticationPrincipal User trainer,@PathVariable Integer statId) {
        statisticChildService.addWin(trainer.getId(),statId);
        return ResponseEntity.ok(new ApiResponse("Win added successfully"));
    }
    @PutMapping("/add-loss/{statId}")
    public ResponseEntity addLossToChild(@AuthenticationPrincipal User trainer,@PathVariable Integer statId) {
        statisticChildService.addLoss(trainer.getId(),statId);
        return ResponseEntity.ok(new ApiResponse("Loss added successfully"));
    }

    @PutMapping("/update-rating/{statId}")
    public ResponseEntity updateChildRating(@AuthenticationPrincipal User trainer,@PathVariable Integer statId) {
        statisticChildService.updateRatingForChild(trainer.getId(),statId);
        return ResponseEntity.ok(new ApiResponse("Child rating updated successfully."));
    }

    @PostMapping("/notify-weak")
    public ResponseEntity notifyParents(@AuthenticationPrincipal User trainer) {
        statisticChildService.notifyParentsIfChildRateIsWeak(trainer.getId());
        return ResponseEntity.ok(new ApiResponse("Emails sent to parents of weak-performing children."));
    }

}
