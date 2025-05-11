package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.StatisticPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player-statistic")
@RequiredArgsConstructor
public class StatisticPlayerController {
    private final StatisticPlayerService statisticPlayerService;

    @PostMapping("/create/{playerId}")
    public ResponseEntity createStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer playerId, @RequestBody @Valid StatisticPlayerDTO dto) {
        statisticPlayerService.createStatisticPlayer(trainerId.getId(), playerId, dto);
        return ResponseEntity.status(201).body("Player statistic created");
    }

    @PutMapping("/update/{statId}")
    public ResponseEntity updateStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId, @RequestBody @Valid StatisticPlayerDTO dto) {
        statisticPlayerService.updateStatistic(trainerId.getId(),statId, dto);
        return ResponseEntity.ok("Player statistic updated");
    }

    @GetMapping("/player")
    public ResponseEntity<StatisticPlayer> getPlayerStatistics(@AuthenticationPrincipal User playerId) {
        return ResponseEntity.ok(statisticPlayerService.getMyStatisticsByPlayerId(playerId.getId()));
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<StatisticPlayer> getPlayerStatistics(@AuthenticationPrincipal User trainerId,@PathVariable Integer playerId) {
        return ResponseEntity.ok(statisticPlayerService.getStatisticsByPlayerId(trainerId.getId(),playerId));
    }

    @GetMapping("/player/trainer")
    public ResponseEntity<List<StatisticPlayer>> getPlayerStatsByTrainer(@AuthenticationPrincipal User trainerId) {
        return ResponseEntity.ok(statisticPlayerService.getAllStatisticsByTrainerId(trainerId.getId()));
    }


    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId) {
        statisticPlayerService.deleteStatistic(trainerId.getId(),statId);
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
