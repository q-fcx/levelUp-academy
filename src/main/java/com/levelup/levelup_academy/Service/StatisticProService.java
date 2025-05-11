package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Repository.ProRepository;
import com.levelup.levelup_academy.Repository.StatisticProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticProService {
    private final StatisticProRepository statisticProRepository;
    private final ProRepository proRepository;

    public StatisticPro getStatisticsByProfessionalId(Integer professionalId) {
        StatisticPro stat = statisticProRepository.findByPro_Id(professionalId);
        if (stat == null) throw new ApiException("Statistic not found for this professional");
        return stat;
    }


    public void createStatistic(Integer proId, StatisticProDTO dto) {
        Pro pro = proRepository.findById(proId)
                .orElseThrow(() -> new ApiException("Pro not found"));

        StatisticPro stat = new StatisticPro(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), pro);

        statisticProRepository.save(stat);
    }

    public void updateStatistic(Integer statId, StatisticProDTO dto) {
        StatisticPro stat = statisticProRepository.findStatisticProById(statId);
        if (stat==null){
            throw new ApiException("Statistic not found");
        }

        stat.setRate(dto.getRate());
        stat.setWinGame(dto.getWinGame());
        stat.setLossGame(dto.getLossGame());
        stat.setTrophy(dto.getTrophy());
        stat.setField(dto.getField());
        stat.setDate(dto.getDate());

        statisticProRepository.save(stat);
    }
    public void deleteStatistic(Integer statId) {
        StatisticPro stat = statisticProRepository.findStatisticProById(statId);
        if (stat==null){
            throw new ApiException("Statistic not found");
        }

        statisticProRepository.delete(stat);
    }
    public StatisticPro getProWithTopTrophy() {
        List<StatisticPro> all = statisticProRepository.findAll();

        return all.stream()
                .filter(pro -> pro.getTrophy() != null)
                .max(Comparator.comparingInt(pro -> getTrophyRank(pro.getTrophy())))
                .orElseThrow(() -> new ApiException("No child has a trophy"));
    }

    private int getTrophyRank(String trophy) {
        return switch (trophy.toUpperCase()) {
            case "GOLD" -> 3;
            case "SILVER" -> 2;
            case "BRONZE" -> 1;
            default -> 0;
        };
    }

    public List<StatisticPro> getTop5ProByGame(Integer winGame) {
        List<StatisticPro> all = statisticProRepository.findStatisticProByWinGame(winGame);
        return all.stream()
                .sorted(Comparator.comparing(StatisticPro::getRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
