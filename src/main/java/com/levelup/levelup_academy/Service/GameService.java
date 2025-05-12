package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Game;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Repository.GameRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ModeratorRepository moderatorRepository;


    //GET
    public List<Game> getAllGames(Integer moderatorId){
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        return gameRepository.findAll();
    }

    //ADD game only by the moderator
    public void addGame(Integer moderatorId,Game game){
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator is not found");
        }
        if (moderator.getUser().getRole() == null || !moderator.getUser().getRole().equals("MODERATOR")) {
            throw new ApiException("Only a moderator can add a game.");
        }
        gameRepository.save(game);
    }

    //Editing the game
    public void editGame(Integer moderatorId,Integer gameId,Game game){
        Game originalGame = gameRepository.findGameById(gameId);
        Moderator moderator= moderatorRepository.findModeratorById(moderatorId);
        if(game==null){
            throw new ApiException("The game you try to edit is not found");
        }
        if (moderator == null){
            throw new ApiException("Moderator is not found");
        }
        if (moderator.getUser().getRole() == null || !moderator.getUser().getRole().equals("MODERATOR")) {
            throw new ApiException("Only a moderator can edit a game.");
        }
        originalGame.setAge(game.getAge());
        originalGame.setName(game.getName());
        originalGame.setCategory(game.getCategory());

        gameRepository.save(originalGame);
    }

    public void deleteGame(Integer moderatorId, Integer gameId){
        Game game = gameRepository.findGameById(gameId);
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(game == null){
            throw new ApiException("The game you try to edit is not found");
        }
        if (moderator == null){
            throw new ApiException("Moderator is not found");
        }
        if (moderator.getUser().getRole() == null || !moderator.getUser().getRole().equals("MODERATOR")) {
            throw new ApiException("Only a moderator can delete a game.");
        }
       gameRepository.delete(game);
    }
}
