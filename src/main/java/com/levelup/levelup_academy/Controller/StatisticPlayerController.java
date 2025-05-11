package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Service.StatisticPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player-statistic")
@RequiredArgsConstructor
public class StatisticPlayerController {
    private final StatisticPlayerService statisticPlayerService;

    @PostMapping("/create/{playerId}")
    public ResponseEntity createStatistic(@PathVariable Integer playerId, @RequestBody @Valid StatisticPlayerDTO dto) {
        statisticPlayerService.createStatistic(playerId, dto);
        return ResponseEntity.status(201).body("Player statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity updateStatistic(@PathVariable Integer statId, @RequestBody @Valid StatisticPlayerDTO dto) {
        statisticPlayerService.updateStatistic(statId, dto);
        return ResponseEntity.ok("Player statistic updated");
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<StatisticPlayer> getPlayerStatistics(@PathVariable Integer playerId) {
        return ResponseEntity.ok(statisticPlayerService.getStatisticsByPlayerId(playerId));
    }



    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@PathVariable Integer statId) {
        statisticPlayerService.deleteStatistic(statId);
        return ResponseEntity.ok("Player statistic deleted");
    }
    @GetMapping("/top-trophy")
    public ResponseEntity<StatisticPlayer> getTopTrophyPlayer() {
        StatisticPlayer top = statisticPlayerService.getPlayerWithTopTrophy();
        return ResponseEntity.ok(top);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<StatisticPlayer>> getTop5ByWinGame(@RequestParam Integer winGame) {
        List<StatisticPlayer> top5 = statisticPlayerService.getTop5PlayersByGame(winGame);
        return ResponseEntity.ok(top5);
    }
}
