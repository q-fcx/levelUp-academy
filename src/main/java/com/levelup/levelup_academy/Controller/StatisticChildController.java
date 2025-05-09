package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Service.StatisticChildService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/child-statistic")
@RequiredArgsConstructor
public class StatisticChildController {
    private final StatisticChildService statisticChildService;

    @PostMapping("/create/{childId}")
    public ResponseEntity<?> createStatistic(@PathVariable Integer childId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.createStatistic(childId, dto);
        return ResponseEntity.status(201).body("Child statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity<?> updateStatistic(@PathVariable Integer statId, @RequestBody @Valid StatisticChildDTO dto) {
        statisticChildService.updateStatistic(statId, dto);
        return ResponseEntity.ok("Child statistic updated");
    }

    @GetMapping("/get/{statId}")
    public ResponseEntity<?> getStatistic(@PathVariable Integer statId) {
        return ResponseEntity.ok(statisticChildService.getStatisticById(statId));
    }

    @DeleteMapping("/delete/{statId}")
    public ResponseEntity<?> deleteStatistic(@PathVariable Integer statId) {
        statisticChildService.deleteStatistic(statId);
        return ResponseEntity.ok("Child statistic deleted");
    }
}
