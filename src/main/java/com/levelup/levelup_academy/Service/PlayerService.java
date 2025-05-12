package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import com.levelup.levelup_academy.Repository.ProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AuthRepository authRepository;
    private final ModeratorRepository moderatorRepository;
    private final ProRepository proRepository;
    private final EmailNotificationService emailNotificationService;

    //GET All players by moderator
    public List<Player> getAllPlayers(Integer moderatorId) {
        Moderator moderator= moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        return playerRepository.findAll();
    }

    public Player getPlayer(Integer moderatorId,Integer playerId){
        Moderator moderator= moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        Player player = playerRepository.findPlayerById(playerId);
        if(player == null){
            throw new ApiException("Player is not found");
        }
        return player;
    }
    //Register player

    public void registerPlayer(PlayerDTO playerDTO){
        playerDTO.setRole("PLAYER");
        String hashPassword = new BCryptPasswordEncoder().encode(playerDTO.getPassword());
        User user = new User(null, playerDTO.getUsername(), hashPassword, playerDTO.getEmail(), playerDTO.getFirstName(), playerDTO.getLastName(), playerDTO.getRole(), LocalDate.now(),null,null,null,null,null,null,null,null);
        Player player = new Player(null,user,null,null);
        authRepository.save(user);
        playerRepository.save(player);
    }
    public void updatePlayer(Integer playerId, PlayerDTO playerDTO) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ApiException("Player not found"));

        User user = player.getUser();
        if (user == null) {
            throw new RuntimeException("User not found for this player");
        }
        String hashPassword = new BCryptPasswordEncoder().encode(playerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setUsername(playerDTO.getUsername());
        user.setEmail(playerDTO.getEmail());
        user.setFirstName(playerDTO.getFirstName());
        user.setLastName(playerDTO.getLastName());

        authRepository.save(user);
        playerRepository.save(player);
    }
    public void deletePlayer(Integer playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        User user = player.getUser();
        if (user != null) {
            authRepository.delete(user);
        }

        playerRepository.delete(player);
    }

//Promote
    public void promotePlayerToPro(Integer moderateId,Integer playerId){
        Player player = playerRepository.findPlayerById(playerId);
        if(player == null){
             throw new ApiException("Player is not found");
        }
        Moderator moderator = moderatorRepository.findModeratorById(moderateId);
        if(moderator == null){
            throw new ApiException("Moderate is not found");
        }
        Integer requireRate = 10;
        if(player.getStatistics().getRate() < requireRate){
            throw new ApiException("Player does not reach enough rate to be promoted to Pro");
        }
        String hashPassword = new BCryptPasswordEncoder().encode(player.getUser().getPassword());
        User user = new User();
        user.setPassword(hashPassword);
        user.setFirstName(player.getUser().getFirstName());
        user.setLastName(player.getUser().getLastName());
        user.setRole("PRO");
        user.setEmail(player.getUser().getEmail());
        user.setUsername(player.getUser().getUsername());

        Pro pro = new Pro();
        pro.setUser(user);
        pro.setIsApproved(true);

        String subject = "Congratulations on Becoming a Pro! 🎉";

        String body = String.format("Dear %s,\n\n" +
                "Congratulations! You have been successfully promoted to a Pro player! 🎉\n\n" +
                "We are thrilled to welcome you to the elite group of Pro players. Your achievements have earned you this well-deserved recognition. Keep up the great work!\n\n" +
                "If you have any questions or need further assistance, feel free to contact support.\n\n" +
                "Best regards,\nTeam", pro.getUser().getUsername());
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipient(pro.getUser().getEmail());
        emailRequest.setSubject(subject);
        emailRequest.setMessage(body);

        emailNotificationService.sendEmail(emailRequest);

        playerRepository.delete(player);
        proRepository.save(pro);


    }

}
