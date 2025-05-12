package com.levelup.levelup_academy.Controller;

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
        return ResponseEntity.status(201).body("Child statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity updateStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.updateStatistic(trainerId.getId(),statId, dto);
        return ResponseEntity.ok("Child statistic updated");
    }

    @GetMapping("/get-child-stati-by-parent/{childId}")
    public ResponseEntity<StatisticChild> getChildStatisticsByParent(@AuthenticationPrincipal User parentId, @PathVariable Integer childId) {
        return ResponseEntity.ok(statisticChildService.getMyChildStatisticsByChildId(parentId.getId(), childId));
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
        return ResponseEntity.ok("Child statistic deleted");
    }
    @GetMapping("/top-child-by-rating")
    public ResponseEntity<String> getTopChildByRating() {
        return ResponseEntity.ok(statisticChildService.getTopChildByRating());
    }

    @GetMapping("/top5")
    public ResponseEntity<List<StatisticChild>> getTop5ByWinGame(@RequestParam Integer winGame) {
        List<StatisticChild> top5 = statisticChildService.getTop5ChildrenByGame(winGame);
        return ResponseEntity.ok(top5);
    }
    @PutMapping("/add-win/{statId}/{trainerId}")
    public ResponseEntity<String> addWinToChild(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticChildService.addWin(trainerId,statId);
        return ResponseEntity.ok("Win added successfully");
    }
    @PutMapping("/add-loss/{statId}/{trainerId}")
    public ResponseEntity<String> addLossToChild(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticChildService.addLoss(trainerId,statId);
        return ResponseEntity.ok("Loss added successfully");
    }

    @PutMapping("/update-rating/{trainerId}/{statId}")
    public ResponseEntity<String> updateChildRating(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticChildService.updateRatingForChild(trainerId,statId);
        return ResponseEntity.ok("Child rating updated successfully.");
    }

    @PostMapping("/notify-weak")
    public ResponseEntity<String> notifyParents() {
        statisticChildService.notifyParentsIfChildRateIsWeak();
        return ResponseEntity.ok("Emails sent to parents of weak-performing children.");
    }

}
