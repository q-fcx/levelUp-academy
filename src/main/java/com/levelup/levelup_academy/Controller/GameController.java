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
    @PostMapping("/add/{moderatorId}")
    public ResponseEntity addGame(@PathVariable Integer moderatorId,@RequestBody @Valid Game game){
        gameService.addGame(moderatorId, game);
        return ResponseEntity.status(200).body(new ApiResponse("Game Added"));
    }

    @PutMapping("/edit/{moderatorId}/{gameId}")
    public ResponseEntity editingGame(@PathVariable Integer moderatorId,@PathVariable Integer gameId,@RequestBody @Valid Game game){
        gameService.editGame(moderatorId, gameId, game);
        return ResponseEntity.status(200).body(new ApiResponse("The Game edited successfully"));
    }

    @DeleteMapping("/delete/{moderatorId}/{gameId}")
    public ResponseEntity deletingGame(@PathVariable Integer moderatorId,@PathVariable Integer gameId){
        gameService.deleteGame(moderatorId, gameId);
        return ResponseEntity.status(200).body(new ApiResponse("The Game deleted successfully"));
    }


}
