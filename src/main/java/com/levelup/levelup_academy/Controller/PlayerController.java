package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.PlayerService;
import com.levelup.levelup_academy.Service.StatisticPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final StatisticPlayerService statisticPlayerService;


     //GET
    @GetMapping("/get")
    public ResponseEntity getAllPlayers(@AuthenticationPrincipal User moderator){
        return ResponseEntity.status(200).body(playerService.getAllPlayers(moderator.getId()));
    }

    //get player by moderator
    @GetMapping("/get-player/{playerId}")
    public ResponseEntity getPlayer(@AuthenticationPrincipal User moderatorId,@PathVariable Integer playerId){
        return ResponseEntity.status(200).body(playerService.getPlayer(moderatorId.getId(), playerId));
    }

    //Register
    @PostMapping("/register")
    public ResponseEntity registerPlayer(@RequestBody @Valid PlayerDTO playerDTO){
        playerService.registerPlayer(playerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Player registers"));
    }
    @PutMapping("/edit")
    public ResponseEntity edit(@AuthenticationPrincipal User playerId, @RequestBody PlayerDTO playerDTO) {
        playerService.updatePlayer(playerId.getId(), playerDTO);
        return ResponseEntity.ok("Player updated successfully");
    }

    // Delete player by ID
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal User playerId) {
        playerService.deletePlayer(playerId.getId());
        return ResponseEntity.ok("Player deleted successfully");
    }

    @GetMapping("/player")
    public ResponseEntity<StatisticPlayer> getPlayerStatistics(@AuthenticationPrincipal User playerId) {
        return ResponseEntity.ok(playerService.getMyStatisticsByPlayerId(playerId.getId()));
    }

}
