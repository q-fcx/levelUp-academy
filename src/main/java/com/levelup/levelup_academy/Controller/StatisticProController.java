package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
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
        return ResponseEntity.status(201).body(new ApiResponse("Pro statistic created"));
    }

    @PutMapping("/update/{stateId}")
    public ResponseEntity updateStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer stateId, @RequestBody @Valid StatisticProDTO dto) {
        statisticProService.updateStatistic(trainerId.getId(), stateId, dto);
        return ResponseEntity.ok(new ApiResponse("Pro statistic updated"));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<StatisticPro> getProfessionalStatistics(@AuthenticationPrincipal User trainerId ,@PathVariable Integer professionalId) {
        return ResponseEntity.ok(statisticProService.getStatisticsByProfessionalId(trainerId.getId(), professionalId));
    }

//    @GetMapping("/professional/trainer/{trainerId}")
//    public ResponseEntity<List<StatisticPro>> getProfessionalStatsByTrainer(@AuthenticationPrincipal User trainerId) {
//        return ResponseEntity.ok(statisticProService.getAllStatisticsByTrainerId(trainerId.getId()));
//    }


    @DeleteMapping("/delete/{statId}")
    public ResponseEntity deleteStatistic(@AuthenticationPrincipal User trainerId,@PathVariable Integer statId) {
        statisticProService.deleteStatistic(trainerId.getId(),statId);
        return ResponseEntity.ok(new ApiResponse("Pro statistic deleted"));
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
    public ResponseEntity addWinToPro(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.addWin(trainerId,statId);
        return ResponseEntity.ok(new ApiResponse("Win added successfully"));
    }
    @PutMapping("/add-loss/{statId}/{trainerId}")
    public ResponseEntity addLossToPro(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.addLoss(trainerId,statId);
        return ResponseEntity.ok(new ApiResponse("Loss added successfully"));
    }
    @PutMapping("/update-rating/{trainerId}/{statId}")
    public ResponseEntity updateProRating(@PathVariable Integer trainerId,@PathVariable Integer statId) {
        statisticProService.updateRatingForPro(trainerId,statId);
        return ResponseEntity.ok(new ApiResponse("Pro rating updated successfully."));
    }

}
