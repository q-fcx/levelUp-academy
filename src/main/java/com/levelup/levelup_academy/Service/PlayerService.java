package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AuthRepository authRepository;

    //GET

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    //Register player

    public void registerPlayer(PlayerDTO playerDTO){
        playerDTO.setRole("PLAYER");
        User user = new User(null, playerDTO.getUsername(), playerDTO.getPassword(), playerDTO.getEmail(), playerDTO.getFirstName(), playerDTO.getLastName(), playerDTO.getRole(), null,null,null,null);
        Player player = new Player(null,user,null, null, null);
        authRepository.save(user);
        playerRepository.save(player);
    }
    public void updatePlayer(Integer id, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        User user = player.getUser();
        if (user == null) {
            throw new RuntimeException("User not found for this player");
        }

        user.setUsername(playerDTO.getUsername());
        user.setPassword(playerDTO.getPassword());
        user.setEmail(playerDTO.getEmail());
        user.setFirstName(playerDTO.getFirstName());
        user.setLastName(playerDTO.getLastName());

        authRepository.save(user);
        playerRepository.save(player);
    }
    public void deletePlayer(Integer id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        User user = player.getUser();
        if (user != null) {
            authRepository.delete(user);
        }

        playerRepository.delete(player);
    }


}
