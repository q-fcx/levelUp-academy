package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.StatisticChildDTO;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.Parent;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Repository.ChildRepository;
import com.levelup.levelup_academy.Repository.ParentRepository;
import com.levelup.levelup_academy.Repository.StatisticChildRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
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
    private final TrainerRepository trainerRepository;
    private final ParentRepository parentRepository;
    private final EmailNotificationService emailNotificationService;





    public StatisticChild getStatisticsByChildId(Integer trainerId,Integer childId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild stat = statisticChildRepository.findByChild_Id(childId);
        if (stat == null) throw new ApiException("Statistic not found for this child");
        return stat;
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

    public void addWin(Integer trainerId, Integer statsId){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild statisticChild = statisticChildRepository.findStatisticChildById(statsId);
        if(statisticChild == null){
            throw new ApiException("Not found");
        }
        statisticChild.setWinGame(statisticChild.getWinGame() + 1);
        statisticChildRepository.save(statisticChild);
    }

    public void addLoss(Integer trainerId, Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild statisticChild = statisticChildRepository.findStatisticChildById(statId);
        if (statisticChild == null) {
            throw new ApiException("Statistic not found");
        }

        statisticChild.setLossGame(statisticChild.getLossGame() + 1);
        statisticChildRepository.save(statisticChild);
    }
    public void updateRatingForChild(Integer trainerId,Integer statId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
        StatisticChild stat = statisticChildRepository.findById(statId)
                .orElseThrow(() -> new ApiException("Statistic not found"));

        int win = stat.getWinGame() != null ? stat.getWinGame() : 0;
        int loss = stat.getLossGame() != null ? stat.getLossGame() : 0;

        double rating = 0.0;
        if (win + loss > 0) {
            rating = (win * 1.0) / (win + loss) * 10;
            rating = Math.min(rating, 10.0);
        }

        stat.setRate(rating);
        statisticChildRepository.save(stat);
    }


    public String getTopChildByRating(Integer trainerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null) throw new ApiException("Trainer not found");
        List<StatisticChild> all = statisticChildRepository.findAll();

        StatisticChild topChild = all.stream()
                .filter(child -> child.getRate() != null)
                .max(Comparator.comparingDouble(StatisticChild::getRate))
                .orElseThrow(() -> new ApiException("No child has a rating"));

        String trophy = getTrophyFromRating(topChild.getRate());
        return topChild.getChild().getParent().getUser().getUsername() + ": " + trophy + " (" + topChild.getRate() + ")";
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

//    public List<StatisticChild> getTop5ChildrenByGame(Integer trainerId,Integer winGame) {
//        Trainer trainer = trainerRepository.findTrainerById(trainerId);
//        if (trainer == null){
//            throw new ApiException("Trainer is not found");
//        }
//        List<StatisticChild> all = statisticChildRepository.findStatisticChildByWinGame(winGame);
//        return all.stream()
//                .sorted(Comparator.comparing(StatisticChild::getRate).reversed())
//                .limit(5)
//                .collect(Collectors.toList());
//    }

//    public void addWin(Integer trainerId,Integer statsId){
//
//        StatisticChild statisticChild = statisticChildRepository.findStatisticChildById(statsId);
//        if(statisticChild == null){
//            throw new ApiException("Not found");
//        }
//        statisticChild.setWinGame(statisticChild.getWinGame() + 1);
//        statisticChildRepository.save(statisticChild);
//    }
    public void notifyParentsIfChildRateIsWeak(Integer trainerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null){
            throw new ApiException("Trainer is not found");
        }
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
