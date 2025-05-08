package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Game;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Repository.GameRepository;
import com.levelup.levelup_academy.Repository.SessionRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
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

    //GET
    public List<Session> getAllClasses(){
        return sessionRepository.findAll();
    }

    //ADD
    public void addClass(Session session,Integer moderator_id){
        Moderator moderator = moderatorRepository.findModeratorById(moderator_id);
        sessionRepository.save(session);

    }


    //Assign Trainer
    public void assignTrainerToSession(Integer trainerId,Integer sessionId){
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        Session session = sessionRepository.findClassById(sessionId);

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

        Session session = sessionRepository.findClassById(sessionId);

//        if(games == null){
//            throw new ApiException("Game not found");
//        }

        if(session == null){
            throw new ApiException("Session not found");
        }
        session.setGame(session.getGame());
        sessionRepository.save(session);

    }


}
