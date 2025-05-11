package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final TrainerRepository trainerRepository;
    private final ModeratorRepository moderatorRepository;
    private final GameRepository gameRepository;
    private final BookingRepository bookingRepository;

    //GET
    public List<Session> getAllClasses(){
        return sessionRepository.findAll();
    }

    //ADD
    public void addClass(Session session,Integer moderator_id,Integer trainerId,Integer gameId){
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


    //Assign Trainer
    public void assignTrainerToSession(Integer trainerId,Integer sessionId){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        Session session = sessionRepository.findSessionById(sessionId);

        if(trainer == null){
            throw new ApiException("Trainer not found");
        }
        if(session == null){
            throw new ApiException("Session not found");
        }
        session.setTrainer(trainer);
        sessionRepository.save(session);
    }

    public void assignGameToTrainer(Integer sessionId ){

        Session session = sessionRepository.findSessionById(sessionId);

//        if(games == null){
//            throw new ApiException("Game not found");
//        }

        if(session == null){
            throw new ApiException("Session not found");
        }
        session.setGame(session.getGame());
        sessionRepository.save(session);

    }

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

    public List<User> getAllPlayersInSession(Integer sessionId) {
        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null) throw new ApiException("Session not found");

        return bookingRepository.findUsersBySessionId(sessionId);
    }



}
