package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.StatisticProService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pro-statistic")
@RequiredArgsConstructor
public class StatisticProController {
    private final StatisticProService statisticProService;

    @PostMapping("/create/{proId}")
    public ResponseEntity createStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer proId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.createStatistic(trainerId.getId(), proId, dto);
        return ResponseEntity.status(201).body("Pro statistic created");
    }

    @PutMapping("/update/{stateId}")
    public ResponseEntity updateStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer stateId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.updateStatistic(trainerId.getId(), stateId, dto);
        return ResponseEntity.ok("Pro statistic updated");
    }

    @GetMapping("/professional")
    public ResponseEntity<StatisticPro> getProfessionalStatistics(@AuthenticationPrincipal User professionalId) {
        return ResponseEntity.ok(statisticProService.getMyStatisticsByProfessionalId(professionalId.getId()));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<StatisticPro> getProfessionalStatistics(@AuthenticationPrincipal User trainerId ,@PathVariable Integer professionalId) {
        return ResponseEntity.ok(statisticProService.getStatisticsByProfessionalId(trainerId.getId(), professionalId));
    }

    @GetMapping("/professional/trainer/{trainerId}")
    public ResponseEntity<List<StatisticPro>> getProfessionalStatsByTrainer(@AuthenticationPrincipal User trainerId) {
        return ResponseEntity.ok(statisticProService.getAllStatisticsByTrainerId(trainerId.getId()));
    }


    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId) {
        statisticProService.deleteStatistic(trainerId.getId(),statId);
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
