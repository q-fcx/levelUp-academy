package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Model.Game;
import com.levelup.levelup_academy.Repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;


    //GET
    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    //ADD
    public void addGame(Game game){
        gameRepository.save(game);
    }
}
