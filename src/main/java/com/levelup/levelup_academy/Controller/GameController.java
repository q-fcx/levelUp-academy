package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.Game;
import com.levelup.levelup_academy.Service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllGames(){
        return ResponseEntity.status(200).body(gameService.getAllGames());
    }

    //ADD
    @PostMapping("/add")
    public ResponseEntity addGame(@RequestBody @Valid Game game){
        gameService.addGame(game);
        return ResponseEntity.status(200).body(new ApiResponse("Game Added"));
    }
}
