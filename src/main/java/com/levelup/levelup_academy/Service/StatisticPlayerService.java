package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import com.levelup.levelup_academy.Repository.StatisticPlayerRepository;
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
    private final EmailNotificationService emailNotificationService;

    public StatisticPlayer getStatisticsByPlayerId(Integer playerId) {
        StatisticPlayer stat = repository.findByPlayer_Id(playerId);
        if (stat == null) throw new ApiException("Statistic not found for this player");
        return stat;
    }


    public void createStatistic(Integer playerId, StatisticPlayerDTO dto) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ApiException("Player not found"));

        StatisticPlayer stat = new StatisticPlayer(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), player);

        repository.save(stat);
    }

    public void updateStatistic(Integer statId, StatisticPlayerDTO dto) {
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

    public void deleteStatistic(Integer statId) {
        StatisticPlayer stat = repository.findStatisticPlayerById(statId);
        if (stat == null) {
            throw new ApiException("Statistic not found");
        }

        repository.delete(stat);
    }

    public void addWin(Integer statsId) {
        StatisticPlayer statisticPlayer = repository.findStatisticPlayerById(statsId);
        if (statisticPlayer == null) {
            throw new ApiException("Not found");
        }
        statisticPlayer.setWinGame(statisticPlayer.getWinGame() + 1);
        repository.save(statisticPlayer);
    }

    public void addLoss(Integer statId) {
        StatisticPlayer statisticPlayer = repository.findStatisticPlayerById(statId);
        if (statisticPlayer == null) {
            throw new ApiException("Not found");
        }

        statisticPlayer.setLossGame(statisticPlayer.getLossGame() + 1);
        repository.save(statisticPlayer);
    }
    public void updateRatingForPlayer(Integer statId) {
        StatisticPlayer stat = repository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        int win = stat.getWinGame() != null ? stat.getWinGame() : 0;
        int loss = stat.getLossGame() != null ? stat.getLossGame() : 0;

        double rating;
        if (win + loss == 0) {
            rating = 0.0;
        } else {
            rating = (win * 1.0) / (win + loss) * 10;
        }

        stat.setRate(rating);
        repository.save(stat);
    }


    public StatisticPlayer getPlayerWithTopTrophy() {
        List<StatisticPlayer> all = repository.findAll();

        return all.stream()
                .filter(player -> player.getTrophy() != null)
                .max(Comparator.comparingInt(player -> getTrophyRank(player.getTrophy())))
                .orElseThrow(() -> new ApiException("No player has a trophy"));
    }

    private int getTrophyRank(String trophy) {
        return switch (trophy.toUpperCase()) {
            case "GOLD" -> 3;
            case "SILVER" -> 2;
            case "BRONZE" -> 1;
            default -> 0;
        };
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