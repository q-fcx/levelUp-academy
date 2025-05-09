package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Repository.ChildRepository;
import com.levelup.levelup_academy.Repository.StatisticChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticChildService {
    private final StatisticChildRepository statisticChildRepository;
    private final ChildRepository childRepository;


    public StatisticChild getStatisticById(Integer statId) {
        return statisticChildRepository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));
    }

    public void createStatistic(Integer childId, StatisticChildDTO dto) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        StatisticChild stat = new StatisticChild(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), child);

        statisticChildRepository.save(stat);
    }

    public void updateStatistic(Integer statId, StatisticChildDTO dto) {
        StatisticChild stat = statisticChildRepository.findById(statId)
                .orElseThrow(() -> new RuntimeException("Statistic not found"));

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
                .orElseThrow(() -> new RuntimeException("Statistic not found"));

        statisticChildRepository.delete(stat);
    }
}
