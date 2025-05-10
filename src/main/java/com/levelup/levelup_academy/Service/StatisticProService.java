package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Repository.ProRepository;
import com.levelup.levelup_academy.Repository.StatisticProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticProService {
    private final StatisticProRepository statisticProRepository;
    private final ProRepository proRepository;

    public StatisticPro getStatisticById(Integer statId) {
        return statisticProRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));
    }

    public void createStatistic(Integer proId, StatisticProDTO dto) {
        Pro pro = proRepository.findById(proId)
                .orElseThrow(() -> new ApiException("Pro not found"));

        StatisticPro stat = new StatisticPro(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), pro);

        statisticProRepository.save(stat);
    }

    public void updateStatistic(Integer statId, StatisticProDTO dto) {
        StatisticPro stat = statisticProRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        stat.setRate(dto.getRate());
        stat.setWinGame(dto.getWinGame());
        stat.setLossGame(dto.getLossGame());
        stat.setTrophy(dto.getTrophy());
        stat.setField(dto.getField());
        stat.setDate(dto.getDate());

        statisticProRepository.save(stat);
    }
    public void deleteStatistic(Integer statId) {
        StatisticPro stat = statisticProRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        statisticProRepository.delete(stat);
    }
    public StatisticPro getTopProByTrophy() {
        return statisticProRepository.findTopByOrderByTrophyDesc();
    }

    public List<StatisticPro> getTop5ProsByGame(String game) {
        return statisticProRepository.findTop5ByPro_GameOrderByRateDesc(game);
    }
}
