package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeratorService {
    private final ModeratorRepository moderatorRepository;
    private final AuthRepository authRepository;

    //GET
    public List<Moderator> getAllModerator(){
        return moderatorRepository.findAll();
    }

    //Register Moderator
    public void registerModerator(ModeratorDTO moderatorDTO){
        moderatorDTO.setRole("MODERATOR");
        User user = new User(null, moderatorDTO.getUsername(), moderatorDTO.getPassword(), moderatorDTO.getEmail(), moderatorDTO.getFirstName(), moderatorDTO.getLastName(), moderatorDTO.getRole(), null,null,null,null);
        Moderator moderator = new Moderator(null,user);
        authRepository.save(user);
        moderatorRepository.save(moderator);
    }
}
