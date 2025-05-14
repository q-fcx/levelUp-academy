package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.DTOOut.PlayerDTOOut;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import com.levelup.levelup_academy.Repository.StatisticPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AuthRepository authRepository;
    private final ModeratorRepository moderatorRepository;
    private final EmailNotificationService emailNotificationService;
    private final UltraMsgService ultraMsgService;
    private final StatisticPlayerRepository statisticPlayerRepository;

    //GET

    public List<PlayerDTOOut> getAllPlayers(Integer moderatorId){

        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        List<Player> players = playerRepository.findAll();

        List<PlayerDTOOut> dtoList = new ArrayList<>();
        for (Player player : players) {
            User user = player.getUser();
            dtoList.add(new PlayerDTOOut(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
        }
        return dtoList;
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
        Player player = new Player(null,user,null);
        authRepository.save(user);
        playerRepository.save(player);

        String subject = "Welcome to LevelUp Academy ";
        String message = "<html><body style='font-family: Arial, sans-serif; color: #fff; line-height: 1.6; background-color: #A53A10; padding: 40px 20px;'>" +
                "<div style='max-width: 600px; margin: auto; background: rgba(255, 255, 255, 0.05); border-radius: 12px; padding: 20px; text-align: center;'>" +
                "<img src='https://i.imgur.com/Q6FtCEu.jpeg' alt='LevelUp Academy Logo' style='width:90px; border-radius: 10px; margin-bottom: 20px;'/>" +
                "<h2 style='color: #fff;'>🎮 Welcome to <span style='color: #FFD700;'>LevelUp Academy</span>, " + playerDTO.getFirstName() + "!</h2>" +
                "<p style='font-size: 16px;'>We're thrilled to have you on board. Get ready to train, play, and level up your skills with an amazing community of players just like you!</p>" +
                "<p style='font-size: 16px;'>👉 <a href='https://discord.gg/3KQPVdrv' style='color: #FFD700; text-decoration: none;'>Join our Discord server</a> to chat, learn, and team up with other LevelUp members!</p>" +
                "<p style='font-size: 15px;'>🚀 Let’s grow stronger together.<br/><b>– The LevelUp Academy Team</b></p>" +
                "</div>" +
                "</body></html>";

        EmailRequest emailRequest = new EmailRequest(playerDTO.getEmail(),message, subject);
        emailNotificationService.sendEmail(emailRequest);

        authRepository.save(user);
        playerRepository.save(player);
//        String proPhoneNumber = "+447723275615";
//        String whatsAppMessage = " New player registered: " + playerDTO.getFirstName() + " " + playerDTO.getLastName() + ".";
//        ultraMsgService.sendWhatsAppMessage(proPhoneNumber, whatsAppMessage);
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

    public StatisticPlayer getMyStatisticsByPlayerId(Integer playerId) {
        StatisticPlayer stat = statisticPlayerRepository.findByPlayer_Id(playerId);
        if (stat == null) throw new ApiException("Statistic not found for this player");
        return stat;
    }


}
