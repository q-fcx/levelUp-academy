package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.*;
import com.levelup.levelup_academy.DTOOut.TrainerDTOOut;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final AuthRepository authRepository;
    private final StatisticChildService statisticChildService;
    private final StatisticPlayerService statisticPlayerService;
    private final StatisticProService statisticProService;
    private final ChildRepository childRepository;
    private final PlayerRepository playerRepository;
    private final ProRepository proRepository;
    private final EmailNotificationService emailNotificationService;
    private final SessionRepository sessionRepository;
    private final BookingRepository bookingRepository;
    private final ModeratorRepository moderatorRepository;
    private final StatisticPlayerRepository statisticPlayerRepository;
    private final StatisticChildRepository statisticChildRepository;

    //GET
    public List<TrainerDTOOut> getAllTrainers(Integer moderatorId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        List<Trainer> trainers = trainerRepository.findAll();

        List<TrainerDTOOut> dtoList = new ArrayList<>();
        for (Trainer trainer : trainers) {
            User user = trainer.getUser();
            dtoList.add(new TrainerDTOOut(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(),user.getTrainer().getSessions()));
        }
        return dtoList;
    }
    //Register Trainer
    public void registerTrainer(TrainerDTO trainerDTO, MultipartFile file){
        trainerDTO.setRole("TRAINER");
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("uploads/cvs/" + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                filePath = path.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save CV file.");
            }
        }
        String hashPassword = new BCryptPasswordEncoder().encode(trainerDTO.getPassword());
        User user = new User(null,trainerDTO.getUsername(),hashPassword,trainerDTO.getEmail(),trainerDTO.getFirstName(),trainerDTO.getLastName(),trainerDTO.getRole(),LocalDate.now(),null,null,null,null,null,null,null,null);
        Trainer trainer = new Trainer(null,filePath,trainerDTO.getIsAvailable(), false, user, null, null);
        authRepository.save(user);
        trainerRepository.save(trainer);
        User admin = authRepository.findUserByRole("ADMIN"); // Replace with actual method if different
        if (admin != null) {
            String message = "<html><body style='font-family: Arial, sans-serif; color: #fff; background-color: #A53A10; padding: 40px 20px;'>" +
                    "<div style='max-width: 600px; margin: auto; background: rgba(255, 255, 255, 0.05); border-radius: 12px; padding: 20px; text-align: center;'>" +
                    "<img src='https://i.imgur.com/Q6FtCEu.jpeg' alt='LevelUp Academy Logo' style='width:90px; border-radius: 10px; margin-bottom: 20px;'/>" +
                    "<h2>📋 New Trainer Registration</h2>" +
                    "<p><b>" + user.getFirstName() + " " + user.getLastName() + "</b> has registered as a Trainer.</p>" +
                    "<p>Email: " + user.getEmail() + "</p>" +
                    "<p>The trainer is awaiting your approval.</p>" +
                    "<p style='font-size: 14px;'>– LevelUp Academy System</p>" +
                    "</div></body></html>";

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setRecipient(admin.getEmail());
            emailRequest.setSubject("New Trainer Awaiting Approval");
            emailRequest.setMessage(message);

            emailNotificationService.sendEmail(emailRequest);
        }
    }

    //download
    public byte[] downloadTrainerCv(Integer adminId, Integer trainerId) {
        User admin = authRepository.findUserById(adminId);
        if(admin == null) throw new ApiException("Admin not found");
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        String filePath = trainer.getCvPath();
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("CV not uploaded for this trainer.");
        }

        try {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            if (!Files.exists(path) || !Files.isReadable(path)) {
                throw new RuntimeException("CV file not found or not readable.");
            }

            return Files.readAllBytes(path);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read CV file", e);
        }
    }
    public void updateTrainer(Integer trainerId, TrainerDTO trainerDTO){
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        User user = trainer.getUser();
        String hashPassword = new BCryptPasswordEncoder().encode(trainerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setUsername(trainerDTO.getUsername());
        user.setPassword(hashPassword);
        user.setEmail(trainerDTO.getEmail());
        user.setFirstName(trainerDTO.getFirstName());
        user.setLastName(trainerDTO.getLastName());

        trainer.setIsAvailable(trainerDTO.getIsAvailable());

        authRepository.save(user);
        trainerRepository.save(trainer);
    }
    public void deleteTrainer(Integer trainerId){
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        authRepository.delete(trainer.getUser());
        trainerRepository.delete(trainer);
    }

    public List<User> getAllPlayersInSession(Integer trainerId, Integer sessionId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null) throw new ApiException("Trainer not found");
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) throw new ApiException("Session not found");

        return bookingRepository.findUsersBySessionId(sessionId);
    }

    //for child
    public void addStatisticToChild(Integer trainerId, Integer childId, StatisticChildDTO statisticChildDTO){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer is not found");
        }
        Child child = childRepository.findChildById(childId);
        if(child == null){
            throw new ApiException("Child not found");
        }

        statisticChildService.createStatisticChild(trainerId,childId,statisticChildDTO);
    }

    //for player
    public void addStatisticToPlayer(Integer trainerId, Integer playerId, StatisticPlayerDTO statisticPlayerDTO){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer not found");
        }
        Player player = playerRepository.findPlayerById(playerId);
        if (player == null){
            throw new ApiException("Player not found");
        }
        statisticPlayerService.createStatisticPlayer(trainerId,playerId,statisticPlayerDTO);

    }

    public void addStatisticToPro(Integer trainerId, Integer proId, StatisticProDTO statisticProDTO){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer not found");
        }
        Pro pro = proRepository.findProById(proId);
        if(pro == null){
            throw new ApiException("Pro not found");
        }
        statisticProService.createStatistic(trainerId,proId,statisticProDTO);
    }

    public void giveTrophyToPlayer(Integer trainerId, Integer playerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer==null){
            throw new ApiException("Trainer not found");
        }

        Player player = playerRepository.findPlayerById(playerId);
        if (player == null) throw new ApiException("Player not found");

        StatisticPlayer stat = player.getStatistics();
        if (stat == null) throw new ApiException("Player statistics not found");

        Double rating = stat.getRate();
        if (rating == null) {
            throw new ApiException("Rating not calculated yet.");
        }

        String trophy = StatisticPlayerService.getTrophyFromRating(rating);

        stat.setTrophy(trophy);
        statisticPlayerRepository.save(stat);
    }


    public void giveTrophyToPro(Integer trainerId, Integer proId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) throw new ApiException("Trainer not found");

        Pro pro = proRepository.findProById(proId);
        if (pro == null) throw new ApiException("pro not found");

        StatisticPro stat = pro.getStatistics();
        if (stat == null) throw new ApiException("pro statistics not found");

        Double rating = stat.getRate();
        if (rating == null) {
            throw new ApiException("Rating not calculated yet.");
        }

        String trophy = StatisticPlayerService.getTrophyFromRating(rating);

        stat.setTrophy(trophy);
//        statisticProRepository.save(stat);
    }

    public void giveTrophyToChild(Integer trainerId, Integer childId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) throw new ApiException("Trainer not found");

        Child child = childRepository.findChildById(childId);
        if (child == null) throw new ApiException("Child not found");

        StatisticChild stat = child.getStatistics();
        if (stat == null) throw new ApiException("Child statistics not found");

        Double rating = stat.getRate();
        if (rating == null) {
            throw new ApiException("Rating not calculated yet.");
        }

        String trophy = StatisticPlayerService.getTrophyFromRating(rating);

        stat.setTrophy(trophy);
        statisticChildRepository.save(stat);
    }




    // Approving Trainer request by admin if the pdf match the requirement
    public void approveTrainerByAdmin(Integer adminId, Integer trainerId) {
        User admin = authRepository.findUserById(adminId);
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Unauthorized: You must be an admin to approve players.");
        }
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) {
            throw new ApiException("The Trainer you are looking for is not found.");
        }
        if (trainer.getIsApproved()) {
            throw new ApiException("This trainer has already been approved.");
        }
        User user = trainer.getUser();
        trainer.setIsApproved(true);
        authRepository.save(user);
        trainerRepository.save(trainer);
    }

    public void rejectTrainerByAdmin(Integer adminId, Integer trainerId) {
        User admin = authRepository.findUserById(adminId);
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Unauthorized: You must be an admin to reject players.");
        }
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if (trainer == null) {
            throw new ApiException("The trainer you are looking for is not found.");
        }

        User user = trainer.getUser();
        trainer.setIsApproved(false);
        trainerRepository.delete(trainer);
        authRepository.delete(user);
    }



    public void sendPromotionRequestToModerator(Integer trainerId,Integer playerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        Player player = playerRepository.findPlayerById(playerId);
        if(trainer == null){
            new ApiException("Trainer not found");
        }
        if(player == null){
            new ApiException("Player not found");
        }
        String subject = "Promotion Request: Player to Pro";
        String body = String.format("Trainer %s has recommended player %s for promotion to Pro.\n\n" +
                        "Reason: %s",
                trainer.getUser().getFirstName(), player.getUser().getUsername());

        List<Moderator> moderators = moderatorRepository.findAll();
        for (Moderator moderator : moderators) {

            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setRecipient(moderator.getUser().getEmail());
            emailRequest.setSubject(subject);
            emailRequest.setMessage(body);
            emailNotificationService.sendEmail(emailRequest);
        }
    }

}
