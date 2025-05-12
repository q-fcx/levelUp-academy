package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.StatisticProDTO;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Model.Trainer;
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


    public void addWin(Integer statsId,Integer trainerId){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPro statisticPro = statisticProRepository.findStatisticProById(statsId);
        if(statisticPro == null){
            throw new ApiException("Not found");
        }
        statisticPro.setWinGame(statisticPro.getWinGame() + 1);
        statisticProRepository.save(statisticPro);
    }

    public void addLoss(Integer statId,Integer trainerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPro statisticPro = statisticProRepository.findStatisticProById(statId);
        if (statisticPro == null) {
            throw new ApiException("Statistic not found");
        }

        statisticPro.setLossGame(statisticPro.getLossGame() + 1);
        statisticProRepository.save(statisticPro);
    }
    public void updateRatingForPro(Integer trainerId,Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticPro stat = statisticProRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        int win = stat.getWinGame() != null ? stat.getWinGame() : 0;
        int loss = stat.getLossGame() != null ? stat.getLossGame() : 0;

        double rating = 0.0;
        if (win + loss > 0) {
            rating = (win * 1.0) / (win + loss) * 10;
            rating = Math.min(rating, 10.0);
        }

        stat.setRate(rating);
        statisticProRepository.save(stat);
    }


    public String getTopProByRating() {
        List<StatisticPro> all = statisticProRepository.findAll();

        StatisticPro topPro = all.stream()
                .filter(pro -> pro.getRate() != null)
                .max(Comparator.comparingDouble(StatisticPro::getRate))
                .orElseThrow(() -> new ApiException("No player has a rating"));

        String trophy = getTrophyFromRating(topPro.getRate());
        return topPro.getPro().getUser().getUsername() + ": " + trophy + " (" + topPro.getRate() + ")";
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

    public List<StatisticPro> getTop5ProByGame(Integer winGame) {
        List<StatisticPro> all = statisticProRepository.findStatisticProByWinGame(winGame);
        return all.stream()
                .sorted(Comparator.comparing(StatisticPro::getRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
