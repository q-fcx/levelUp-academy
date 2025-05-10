package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Service.StatisticProService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pro-statistic")
@RequiredArgsConstructor
public class StatisticProController {
    private final StatisticProService statisticProService;

    @PostMapping("/create/{proId}")
    public ResponseEntity createStatistic(@PathVariable Integer proId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.createStatistic(proId, dto);
        return ResponseEntity.status(201).body("Pro statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity updateStatistic(@PathVariable Integer statId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.updateStatistic(statId, dto);
        return ResponseEntity.ok("Pro statistic updated");
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<StatisticPro> getProfessionalStatistics(@PathVariable Integer professionalId) {
        return ResponseEntity.ok(statisticProService.getStatisticsByProfessionalId(professionalId));
    }

    @GetMapping("/professional/trainer/{trainerId}")
    public ResponseEntity<List<StatisticPro>> getProfessionalStatsByTrainer(@PathVariable Integer trainerId) {
        return ResponseEntity.ok(statisticProService.getAllStatisticsByTrainerId(trainerId));
    }


    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@PathVariable Integer statId) {
        statisticProService.deleteStatistic(statId);
        return ResponseEntity.ok("Pro statistic deleted");
    }
    @GetMapping("/top-trophy")
    public ResponseEntity<StatisticPro> getTopTrophyChild() {
        StatisticPro top = statisticProService.getProWithTopTrophy();
        return ResponseEntity.ok(top);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<StatisticPro>> getTop5ByWinGame(@RequestParam Integer winGame) {
        List<StatisticPro> top5 = statisticProService.getTop5ProByGame(winGame);
        return ResponseEntity.ok(top5);
    }
}
