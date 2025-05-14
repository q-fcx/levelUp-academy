package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final TrainerRepository trainerRepository;
    private final ModeratorRepository moderatorRepository;
    private final GameRepository gameRepository;
    private final BookingRepository bookingRepository;
    private final EmailNotificationService emailNotificationService;

    //GET
    public List<Session> getAllClasses(){
        return sessionRepository.findAll();
    }

    //ADD
    public void addClass(Integer moderator_id, Session session,Integer trainerId,Integer gameId){
        Moderator moderator = moderatorRepository.findModeratorById(moderator_id);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null){
            throw new ApiException("Trainer not found");
        }
        Game game = gameRepository.findGameById(gameId);
        if(game == null){
            throw new ApiException("Game not found");
        }
        session.setTrainer(trainer);
        session.setGame(game);
        sessionRepository.save(session);

    }


//    //Assign Trainer
//    public void assignTrainerToSession(Integer trainerId,Integer sessionId){
//        Trainer trainer = trainerRepository.findTrainerById(trainerId);
//        Session session = sessionRepository.findSessionById(sessionId);
//
//        if(trainer == null){
//            throw new ApiException("Trainer not found");
//        }
//        if(session == null){
//            throw new ApiException("Session not found");
//        }
//        session.setTrainer(trainer);
//        sessionRepository.save(session);
//    }
//
//    public void assignGameToTrainer(Integer sessionId ){
//
//        Session session = sessionRepository.findSessionById(sessionId);
//
////        if(games == null){
////            throw new ApiException("Game not found");
////        }
//
//        if(session == null){
//            throw new ApiException("Session not found");
//        }
//        session.setGame(session.getGame());
//        sessionRepository.save(session);
//
//    }

    //update session
    public void updateSession(Integer moderatorId, Session session, Integer sessionId){
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        Session oldSession = sessionRepository.findSessionById(sessionId);
        if(oldSession == null){
            throw new ApiException("Session not found");
        }

        oldSession.setGame(session.getGame());
        oldSession.setName(session.getName());
        oldSession.setTrainer(session.getTrainer());
        oldSession.setTime(session.getTime());
        sessionRepository.save(oldSession);
    }

    public void deleteSession(Integer moderatorId, Integer sessionId){
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        Session delSession = sessionRepository.findSessionById(sessionId);
        if(delSession == null){
            throw new ApiException("Session not found");
        }

        sessionRepository.delete(delSession);
    }
    public void notifyUsersIfSessionStarting(Integer moderatorId, Integer sessionId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) throw new ApiException("Session not found");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime sessionTime = LocalTime.parse(session.getTime(), formatter);
        LocalTime now = LocalTime.now();

        if (!now.isBefore(sessionTime)) {
            throw new ApiException("Session has not started yet.");
        }

        List<Booking> bookings = bookingRepository.findBookingBySessionId(sessionId);
        if (bookings.isEmpty()) throw new ApiException("No users booked for this session.");

        for (Booking booking : bookings) {
            User user = booking.getUser();
            String message = "<html><body style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px;'>" +
                    "<div style='max-width: 600px; margin: auto; background-color: #ffffff; padding: 25px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>" +
                    "<h2 style='color: #A53A10;'>🎮 Session Starting Now!</h2>" +
                    "<p style='font-size: 16px; color: #333;'>Hi <b>" + user.getFirstName() + "</b>,</p>" +
                    "<p style='font-size: 16px; color: #333;'>Your session <b>" + session.getName() + "</b> is starting now!</p>" +
                    "<div style='background-color: #f9f9f9; padding: 15px; border-radius: 8px; margin: 20px 0; font-size: 15px;'>" +
                    "<p><b>🎮 Game:</b> " + session.getGame().getName() + "</p>" +
                    "<p><b>🕒 Start Time:</b> " + session.getTime() + "</p>" +
                    "</div>" +
                    "<p style='font-size: 16px; color: #333;'>Good luck and have fun!</p>" +
                    "<p style='font-size: 14px; color: #555;'>– The LevelUp Academy Team</p>" +
                    "</div></body></html>";


            EmailRequest email = new EmailRequest();
            email.setRecipient(user.getEmail());
            email.setSubject("🎮 Your Session is Starting Now!");
            email.setMessage(message);

            emailNotificationService.sendEmail(email);
        }
    }

    public void changeTrainerSession(Integer moderatorId, Integer trainerId, Integer newSessionId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        Session newSession = sessionRepository.findSessionById(newSessionId);

        if (trainer == null) {
            throw new ApiException("Trainer not found");
        }

        if (newSession == null) {
            throw new ApiException("New session not found");
        }

        Session oldSession = sessionRepository.findSessionsByTrainer_Id(trainerId);
        if (oldSession != null) {
            oldSession.setTrainer(null);
            sessionRepository.save(oldSession);
        }

        newSession.setTrainer(trainer);
        sessionRepository.save(newSession);
    }




}
