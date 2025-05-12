package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticProDTO;
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

    @PostMapping("/create/{proId}/{trainerId}")
    public ResponseEntity createStatistic(@PathVariable Integer trainerId,@PathVariable Integer proId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.createStatistic(trainerId,proId, dto);
        return ResponseEntity.status(201).body("Pro statistic created");
    }

    @PutMapping("/update/{statId}/{trainerId}")
    public ResponseEntity updateStatistic(@PathVariable Integer trainerId,@PathVariable Integer statId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.updateStatistic(trainerId,statId, dto);
        return ResponseEntity.ok("Pro statistic updated");
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<StatisticPro> getProfessionalStatistics(@PathVariable Integer professionalId) {
        return ResponseEntity.ok(statisticProService.getStatisticsByProfessionalId(professionalId));
    }

    @DeleteMapping("/delete/{statId}/{trainerId}")
    public ResponseEntity deleteStatistic(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.deleteStatistic(trainerId,statId);
        return ResponseEntity.ok("Pro statistic deleted");
    }
    @GetMapping("/top-pro-by-rating")
    public ResponseEntity<String> getTopProByRating() {
        return ResponseEntity.ok(statisticProService.getTopProByRating());
    }

    @GetMapping("/top5")
    public ResponseEntity<List<StatisticPro>> getTop5ByWinGame(@RequestParam Integer winGame) {
        List<StatisticPro> top5 = statisticProService.getTop5ProByGame(winGame);
        return ResponseEntity.ok(top5);
    }
    @PutMapping("/add-win/{statId}/{trainerId}")
    public ResponseEntity<String> addWinToPro(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.addWin(trainerId,statId);
        return ResponseEntity.ok("Win added successfully");
    }
    @PutMapping("/add-loss/{statId}/{trainerId}")
    public ResponseEntity<String> addLossToPro(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.addLoss(trainerId,statId);
        return ResponseEntity.ok("Loss added successfully");
    }
    @PutMapping("/update-rating/{trainerId}/{statId}")
    public ResponseEntity<String> updateProRating(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.updateRatingForPro(trainerId,statId);
        return ResponseEntity.ok("Pro rating updated successfully.");
    }

}
