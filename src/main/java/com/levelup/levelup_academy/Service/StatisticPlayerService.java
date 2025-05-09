package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.StatisticPlayerDTO;
import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import com.levelup.levelup_academy.Repository.StatisticPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticPlayerService {
    private final StatisticPlayerRepository repository;
    private final PlayerRepository playerRepository;

    public StatisticPlayer getStatisticById(Integer statId) {
        return repository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));
    }


    public void createStatistic(Integer playerId, StatisticPlayerDTO dto) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        StatisticPlayer stat = new StatisticPlayer(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), player);

        repository.save(stat);
    }
    public void updateStatistic(Integer statId, StatisticPlayerDTO dto) {
        StatisticPlayer stat = repository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));

        stat.setRate(dto.getRate());
        stat.setWinGame(dto.getWinGame());
        stat.setLossGame(dto.getLossGame());
        stat.setTrophy(dto.getTrophy());
        stat.setField(dto.getField());
        stat.setDate(dto.getDate());

        repository.save(stat);
    }
    public void deleteStatistic(Integer statId) {
        StatisticPlayer stat = repository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));

        repository.delete(stat);
    }

}
