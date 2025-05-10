package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.Service.PlayerService;
import com.levelup.levelup_academy.Service.StatisticPlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final StatisticPlayerService statisticPlayerService;
//GET
    @GetMapping("/get")
    public ResponseEntity getAllPlayers(){
        return ResponseEntity.status(200).body(playerService.getAllPlayers());
    }

    //Register
    @PostMapping("/register")
    public ResponseEntity registerPlayer(@RequestBody @Valid PlayerDTO playerDTO){
        playerService.registerPlayer(playerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Player registers"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody PlayerDTO playerDTO) {
        playerService.updatePlayer(id, playerDTO);
        return ResponseEntity.ok("Player updated successfully");
    }

    // Delete player by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok("Player deleted successfully");
    }

}
