package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import com.levelup.levelup_academy.Repository.StatisticPlayerRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticPlayerService {
    private final StatisticPlayerRepository repository;
    private final PlayerRepository playerRepository;
    private final TrainerRepository trainerRepository;
    private final EmailNotificationService emailNotificationService;


    public StatisticPlayer getMyStatisticsByPlayerId(Integer playerId) {
        StatisticPlayer stat = repository.findByPlayer_Id(playerId);
        if (stat == null) throw new ApiException("Statistic not found for this player");
        return stat;
    }

    public StatisticPlayer getStatisticsByPlayerId(Integer trainerId,Integer playerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPlayer stat = repository.findByPlayer_Id(playerId);
        if (stat == null) throw new ApiException("Statistic not found for this player");
        return stat;
    }


    public void createStatisticPlayer(Integer trainerId ,Integer playerId, StatisticPlayerDTO dto) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        Player player = playerRepository.findPlayerById(playerId);
        if(player == null){
            throw new ApiException("Player not fount");
        }

        StatisticPlayer stat = new StatisticPlayer(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), player);

        repository.save(stat);
    }
    public void updateStatistic(Integer trainerId,Integer statId, StatisticPlayerDTO dto) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPlayer stat = repository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        stat.setRate(dto.getRate());
        stat.setWinGame(dto.getWinGame());
        stat.setLossGame(dto.getLossGame());
        stat.setTrophy(dto.getTrophy());
        stat.setField(dto.getField());
        stat.setDate(dto.getDate());

        repository.save(stat);
    }
    public void deleteStatistic(Integer trainerId,Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPlayer stat = repository.findStatisticPlayerById(statId);
        if (stat == null) {
            throw new ApiException("Statistic not found");
        }

        repository.delete(stat);
    }

    public void addWin(Integer statsId,Integer trainerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPlayer statisticPlayer = repository.findStatisticPlayerById(statsId);
        if (statisticPlayer == null) {
            throw new ApiException("Not found");
        }
        statisticPlayer.setWinGame(statisticPlayer.getWinGame() + 1);
        repository.save(statisticPlayer);
    }

    public void addLoss(Integer statId,Integer trainerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPlayer statisticPlayer = repository.findStatisticPlayerById(statId);
        if (statisticPlayer == null) {
            throw new ApiException("Not found");
        }

        statisticPlayer.setLossGame(statisticPlayer.getLossGame() + 1);
        repository.save(statisticPlayer);
    }
    public void updateRatingForPlayer(Integer trainerId,Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPlayer stat = repository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        int win = stat.getWinGame() != null ? stat.getWinGame() : 0;
        int loss = stat.getLossGame() != null ? stat.getLossGame() : 0;

        double rating = 0.0;
        if (win + loss > 0) {
            rating = (win * 1.0) / (win + loss) * 10;
            rating = Math.min(rating, 10.0);
        }

        stat.setRate(rating);
        repository.save(stat);
    }


    public String getTopPlayerByRating() {
        List<StatisticPlayer> all = repository.findAll();

        StatisticPlayer topPlayer = all.stream()
                .filter(player -> player.getRate() != null)
                .max(Comparator.comparingDouble(StatisticPlayer::getRate))
                .orElseThrow(() -> new ApiException("No player has a rating"));

        String trophy = getTrophyFromRating(topPlayer.getRate());
        return topPlayer.getPlayer().getUser().getUsername() + ": " + trophy + " (" + topPlayer.getRate() + ")";
    }

    public static String getTrophyFromRating(double rating) {
        if (rating >= 9.0) {
            return "GOLD";
        } else if (rating >= 6.0) {
            return "SILVER";
        } else if (rating > 0.0) {
            return "BRONZE";
        } else {
            return "NO_TROPHY";
        }
    }

    public List<StatisticPlayer> getTop5PlayersByGame(Integer winGame) {
        List<StatisticPlayer> all = repository.findStatisticPlayerByWinGame(winGame);
        return all.stream()
                .sorted(Comparator.comparing(StatisticPlayer::getRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public void notifyPlayerIfRateIsWeak() {
        List<StatisticPlayer> allStats = repository.findAll();

        for (StatisticPlayer stat : allStats) {
            if (stat.getWinGame() <= 1 || stat.getLossGame() >= 5) {
                Player player = stat.getPlayer();
                if (player != null) {
                    EmailRequest email = new EmailRequest();
                    email.setRecipient(player.getUser().getEmail());
                    email.setSubject("Low Performance Alert");
                    email.setMessage("your rating is currently low");

                    emailNotificationService.sendEmail(email);
                }
            }

        }
    }
}