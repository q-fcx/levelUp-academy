package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
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

    @GetMapping("/player/{playerId}")
    public ResponseEntity<StatisticPlayer> getPlayerStatistics(@AuthenticationPrincipal User trainerId,@PathVariable Integer playerId) {
        return ResponseEntity.ok(statisticPlayerService.getStatisticsByPlayerId(trainerId.getId(),playerId));
    }

//    @GetMapping("/player/trainer")
//    public ResponseEntity<List<StatisticPlayer>> getPlayerStatsByTrainer(@AuthenticationPrincipal User trainerId) {
//        return ResponseEntity.ok(statisticPlayerService.getAllStatisticsByTrainerId(trainerId.getId()));
//    }


    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId) {
        statisticPlayerService.deleteStatistic(trainerId.getId(),statId);
        return ResponseEntity.ok("Player statistic deleted");
    }
    @GetMapping("/top-player-by-rating")
    public ResponseEntity<String> getTopPlayerByRating(@AuthenticationPrincipal User trainer) {
        return ResponseEntity.ok(statisticPlayerService.getTopPlayerByRating(trainer.getId()));
    }

    @GetMapping("/player/{playerId}/{trainerId}")
    public ResponseEntity<StatisticPlayer> getPlayerStatistics(@PathVariable Integer trainerId,@PathVariable Integer playerId) {
        return ResponseEntity.ok(statisticPlayerService.getStatisticsByPlayerId(trainerId,playerId));
    }
//    @GetMapping("/top5")
//    public ResponseEntity<List<StatisticPlayer>> getTop5ByWinGame(@RequestParam Integer winGame) {
//        List<StatisticPlayer> top5 = statisticPlayerService.getTop5PlayersByGame(winGame);
//        return ResponseEntity.ok(top5);
//    }
    @PutMapping("/add-win/{statId}/{trainerId}")
    public ResponseEntity<String> addWinToPlayer(@PathVariable Integer statId,@PathVariable Integer trainerId) {
        statisticPlayerService.addWin(statId,trainerId);
        return ResponseEntity.ok("Win added successfully");
    }
    @PutMapping("/add-loss/{statId}/{trainerId}")
    public ResponseEntity<String> addLossToPlayer(@PathVariable Integer statId,@PathVariable Integer trainerId) {
        statisticPlayerService.addLoss(statId,trainerId);
        return ResponseEntity.ok("Loss added successfully");

    }
    @PutMapping("/update-rating/{trainerId}/{statId}")
    public ResponseEntity<String> updatePlayerRating(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticPlayerService.updateRatingForPlayer(trainerId,statId);
        return ResponseEntity.ok("Player rating updated successfully.");
    }

    @PostMapping("/notify-weak")
    public ResponseEntity<String> notifyPlayer(@AuthenticationPrincipal User trainer) {
        statisticPlayerService.notifyPlayerIfRateIsWeak(trainer.getId());
        return ResponseEntity.ok("Emails sent to player of weak-performing.");
    }
}
