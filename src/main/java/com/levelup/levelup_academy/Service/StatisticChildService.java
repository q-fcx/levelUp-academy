package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Repository.ChildRepository;
import com.levelup.levelup_academy.Repository.StatisticChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticChildService {
    private final StatisticChildRepository statisticChildRepository;
    private final ChildRepository childRepository;


    //get all
    //get my stat
    public StatisticChild getStatisticById(Integer statId) {
        return statisticChildRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));
    }

    public void createStatistic(Integer childId, StatisticChildDTO dto) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ApiException("Child not found"));

        StatisticChild stat = new StatisticChild(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), child);

        statisticChildRepository.save(stat);
    }

    public void updateStatistic(Integer statId, StatisticChildDTO dto) {
        StatisticChild stat = statisticChildRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        stat.setRate(dto.getRate());
        stat.setWinGame(dto.getWinGame());
        stat.setLossGame(dto.getLossGame());
        stat.setTrophy(dto.getTrophy());
        stat.setField(dto.getField());
        stat.setDate(dto.getDate());

        statisticChildRepository.save(stat);
    }
    public void deleteStatistic(Integer statId) {
        StatisticChild stat = statisticChildRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        statisticChildRepository.delete(stat);
    }
    public StatisticChild getTopChildByTrophy() {
        return statisticChildRepository.findTopByOrderByTrophyDesc();
    }

    public List<StatisticChild> getTop5ChildrenByGame(String game) {
        return statisticChildRepository.findTop5ByChild_GameOrderByRateDesc(game);
    }
}
