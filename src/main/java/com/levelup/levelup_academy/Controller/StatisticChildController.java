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

    @PostMapping("/create/{childId}")
    public ResponseEntity createStatistic(@PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.createStatistic(childId, dto);
        return ResponseEntity.status(201).body("Child statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity updateStatistic(@PathVariable Integer statId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.updateStatistic(statId, dto);
        return ResponseEntity.ok("Child statistic updated");
    }

    @GetMapping("/by-child/{childId}")
    public ResponseEntity<StatisticChild> getChildStatistics(@PathVariable Integer childId) {
        return ResponseEntity.ok(statisticChildService.getStatisticsByChildId(childId));
    }

    @GetMapping("/by-trainer/{trainerId}")
    public ResponseEntity<List<StatisticChild>> getAllStatsByTrainer(@PathVariable Integer trainerId) {
        return ResponseEntity.ok(statisticChildService.getAllStatisticsByTrainerId(trainerId));
    }

    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@PathVariable Integer statId) {
        statisticChildService.deleteStatistic(statId);
        return ResponseEntity.ok("Child statistic deleted");
    }
    @GetMapping("/top-trophy")
    public ResponseEntity<StatisticChild> getTopTrophyChild() {
        StatisticChild top = statisticChildService.getChildWithTopTrophy();
        return ResponseEntity.ok(top);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<StatisticChild>> getTop5ByWinGame(@RequestParam Integer winGame) {
        List<StatisticChild> top5 = statisticChildService.getTop5ChildrenByGame(winGame);
        return ResponseEntity.ok(top5);
    }
}
