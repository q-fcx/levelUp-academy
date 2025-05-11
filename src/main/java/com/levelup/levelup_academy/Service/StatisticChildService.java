package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Repository.ChildRepository;
import com.levelup.levelup_academy.Repository.StatisticChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticChildService {
    private final StatisticChildRepository statisticChildRepository;
    private final ChildRepository childRepository;
    private final EmailNotificationService emailNotificationService;


    public StatisticChild getStatisticsByChildId(Integer childId) {
        StatisticChild stat = statisticChildRepository.findByChild_Id(childId);
        if (stat == null) throw new ApiException("Statistic not found for this child");
        return stat;
    }

    public void createStatistic(Integer childId, StatisticChildDTO dto) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ApiException("Child not found"));

        StatisticChild stat = new StatisticChild(null, dto.getRate(), dto.getWinGame(), dto.getLossGame(),
                dto.getTrophy(), dto.getField(), dto.getDate(), child);

        statisticChildRepository.save(stat);
    }

    public void updateStatistic(Integer statId, StatisticChildDTO dto) {
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
    public void deleteStatistic(Integer statId) {
        StatisticChild stat = statisticChildRepository.findStatisticChildById(statId);
        if (stat==null){
            throw new ApiException("Statistic not found");
        }
        statisticChildRepository.delete(stat);
    }

    public void addWin(Integer statsId){
        StatisticChild statisticChild = statisticChildRepository.findStatisticChildById(statsId);
        if(statisticChild == null){
            throw new ApiException("Not found");
        }
        statisticChild.setWinGame(statisticChild.getWinGame() + 1);
        statisticChildRepository.save(statisticChild);
    }

    public void addLoss(Integer statId) {
        StatisticChild statisticChild = statisticChildRepository.findStatisticChildById(statId);
        if (statisticChild == null) {
            throw new ApiException("Statistic not found");
        }

        statisticChild.setLossGame(statisticChild.getLossGame() + 1);
        statisticChildRepository.save(statisticChild);
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
    public void notifyParentsIfChildRateIsWeak() {
        List<StatisticChild> allStats = statisticChildRepository.findAll();

        for (StatisticChild stat : allStats) {
            if (stat.getWinGame() <= 1 || stat.getLossGame() >= 5) {
                Child child = stat.getChild();
                if (child != null && child.getParent() != null && child.getParent().getUser() != null) {
                    EmailRequest email = new EmailRequest();
                    email.setRecipient(child.getParent().getUser().getEmail());
                    email.setSubject( "Low Performance Alert for Your Child");
                    email.setMessage( "Your child is performing poorly. Please review their training and progress.");

                    emailNotificationService.sendEmail(email);
                }
            }
        }
    }

}
