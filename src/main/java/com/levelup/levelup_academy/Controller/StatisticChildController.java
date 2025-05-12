package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Service.StatisticChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/child-statistic")
@RequiredArgsConstructor
public class StatisticChildController {
    private final StatisticChildService statisticChildService;

    @PostMapping("/create/{childId}/{trainerId}")
    public ResponseEntity createStatistic(@PathVariable Integer trainerId,@PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.createStatistic(trainerId,childId, dto);
        return ResponseEntity.status(201).body("Child statistic created");
    }

    @PutMapping("/update/{statId}/{trainerId}")
    public ResponseEntity updateStatistic(@PathVariable Integer trainerId,@PathVariable Integer statId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.updateStatistic(trainerId,statId, dto);
        return ResponseEntity.ok("Child statistic updated");
    }

    @GetMapping("/by-child/{childId}")
    public ResponseEntity<StatisticChild> getChildStatistics(@PathVariable Integer childId) {
        return ResponseEntity.ok(statisticChildService.getStatisticsByChildId(childId));
    }


    @DeleteMapping("/delete/{statId}/{trainerId}")
    public ResponseEntity deleteStatistic(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticChildService.deleteStatistic(trainerId,statId);
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
