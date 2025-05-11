package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.Parent;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Repository.ChildRepository;
import com.levelup.levelup_academy.Repository.ParentRepository;
import com.levelup.levelup_academy.Repository.StatisticChildRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.awt.print.Pageable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticChildService {
    private final StatisticChildRepository statisticChildRepository;
    private final ChildRepository childRepository;
    private final TrainerRepository trainerRepository;
    private final ParentRepository parentRepository;


    public StatisticChild getMyChildStatisticsByChildId(Integer parentId,Integer childId) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null){
            throw new ApiException("Parent not found");
        }
        StatisticChild stat = statisticChildRepository.findByChild_Id(childId);
        if (stat == null) throw new ApiException("Statistic not found for this child");
        return stat;
    }

    public StatisticChild getStatisticsByChildId(Integer trainerId,Integer childId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild stat = statisticChildRepository.findByChild_Id(childId);
        if (stat == null) throw new ApiException("Statistic not found for this child");
        return stat;
    }
    public List<StatisticChild> getAllStatisticsByTrainerId(Integer trainerId) {
        return statisticChildRepository.findByChild_Trainer_Id(trainerId);
    }

    public void createStatisticChild(Integer trainerId,Integer childId, StatisticChildDTO dto) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ApiException("Child not found"));

        StatisticChild stat = new StatisticChild(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), child);

        statisticChildRepository.save(stat);
    }

    public void updateStatistic(Integer trainerId,Integer statId, StatisticChildDTO dto) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild stat = statisticChildRepository.findStatisticChildById(statId);
        if (stat==null){
            throw new ApiException("Statistic not found");
        }

        stat.setRate(dto.getRate());
        stat.setWinGame(dto.getWinGame());
        stat.setLossGame(dto.getLossGame());
        stat.setTrophy(dto.getTrophy());
        stat.setField(dto.getField());
        stat.setDate(dto.getDate());

        statisticChildRepository.save(stat);
    }
    public void deleteStatistic(Integer trainerId,Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild stat = statisticChildRepository.findStatisticChildById(statId);
        if (stat==null){
            throw new ApiException("Statistic not found");
        }
        statisticChildRepository.delete(stat);
    }
    public StatisticChild getChildWithTopTrophy() {
        List<StatisticChild> all = statisticChildRepository.findAll();

        return all.stream()
                .filter(child -> child.getTrophy() != null)
                .max(Comparator.comparingInt(child -> getTrophyRank(child.getTrophy())))
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

    public List<StatisticChild> getTop5ChildrenByGame(Integer winGame) {
        List<StatisticChild> all = statisticChildRepository.findStatisticChildByWinGame(winGame);
        return all.stream()
                .sorted(Comparator.comparing(StatisticChild::getRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
