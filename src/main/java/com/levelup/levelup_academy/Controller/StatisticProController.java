package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Service.StatisticProService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pro-statistic")
@RequiredArgsConstructor
public class StatisticProController {
    private final StatisticProService statisticProService;

    @PostMapping("/create/{proId}")
    public ResponseEntity<?> createStatistic(@PathVariable Integer proId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.createStatistic(proId, dto);
        return ResponseEntity.status(201).body("Pro statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity<?> updateStatistic(@PathVariable Integer statId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.updateStatistic(statId, dto);
        return ResponseEntity.ok("Pro statistic updated");
    }

    @GetMapping("/get/{statId}")
    public ResponseEntity<?> getStatistic(@PathVariable Integer statId) {
        return ResponseEntity.ok(statisticProService.getStatisticById(statId));
    }

    @DeleteMapping("/delete/{statId}")
    public ResponseEntity<?> deleteStatistic(@PathVariable Integer statId) {
        statisticProService.deleteStatistic(statId);
        return ResponseEntity.ok("Pro statistic deleted");
    }
}
