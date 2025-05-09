package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.Service.StatisticPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/player-statistic")
@RequiredArgsConstructor
public class StatisticPlayerController {
    private final StatisticPlayerService statisticPlayerService;

    @PostMapping("/create/{playerId}")
    public ResponseEntity<?> createStatistic(@PathVariable Integer playerId, @RequestBody @Valid StatisticPlayerDTO dto) {
        statisticPlayerService.createStatistic(playerId, dto);
        return ResponseEntity.status(201).body("Player statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity<?> updateStatistic(@PathVariable Integer statId, @RequestBody @Valid StatisticPlayerDTO dto) {
        statisticPlayerService.updateStatistic(statId, dto);
        return ResponseEntity.ok("Player statistic updated");
    }

    @GetMapping("/get/{statId}")
    public ResponseEntity<?> getStatistic(@PathVariable Integer statId) {
        return ResponseEntity.ok(statisticPlayerService.getStatisticById(statId));
    }

    @DeleteMapping("/delete/{statId}")
    public ResponseEntity<?> deleteStatistic(@PathVariable Integer statId) {
        statisticPlayerService.deleteStatistic(statId);
        return ResponseEntity.ok("Player statistic deleted");
    }
}
