package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.ProRepository;
import com.levelup.levelup_academy.Repository.StatisticProRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
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
    private final TrainerRepository trainerRepository;

    //Get my statistic
    public StatisticPro getMyStatisticsByProfessionalId(Integer professionalId) {
        StatisticPro stat = statisticProRepository.findByPro_Id(professionalId);
        if (stat == null) throw new ApiException("Statistic not found for this professional");
        return stat;
    }

    //get one pro statistic by trainer
    public StatisticPro getStatisticsByProfessionalId(Integer trainerId,Integer professionalId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPro stat = statisticProRepository.findByPro_Id(professionalId);
        if (stat == null) throw new ApiException("Statistic not found for this professional");
        return stat;
    }

    public List<StatisticPro> getAllStatisticsByTrainerId(Integer trainerId) {
        return statisticProRepository.findByPro_Trainer_Id(trainerId);
    }


    public void createStatistic(Integer trainerId,Integer proId, StatisticProDTO dto) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        Pro pro = proRepository.findProById(proId);
        if(pro == null){
       throw new ApiException("Pro not found");
        }
        StatisticPro stat = new StatisticPro(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), pro);

        statisticProRepository.save(stat);
    }

    public void updateStatistic(Integer trainerId,Integer statId, StatisticProDTO dto) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
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
    public void deleteStatistic(Integer trainerId,Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
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
