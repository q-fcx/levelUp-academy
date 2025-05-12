package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.Game;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllGames(@AuthenticationPrincipal User moderator){
        return ResponseEntity.status(200).body(gameService.getAllGames(moderator.getId()));
    }

    //ADD
    @PostMapping("/add")
    public ResponseEntity addGame(@AuthenticationPrincipal User moderator, @RequestBody @Valid Game game){
        gameService.addGame(moderator.getId(), game);
        return ResponseEntity.status(200).body(new ApiResponse("Game Added"));
    }

    @PutMapping("/edit/{gameId}")
    public ResponseEntity editingGame(@AuthenticationPrincipal User moderator,@PathVariable Integer gameId,@RequestBody @Valid Game game){
        gameService.editGame(moderator.getId(), gameId, game);
        return ResponseEntity.status(200).body(new ApiResponse("The Game edited successfully"));
    }

    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity deletingGame(@AuthenticationPrincipal User moderator,@PathVariable Integer gameId){
        gameService.deleteGame(moderator.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("The Game deleted successfully"));
    }


}
