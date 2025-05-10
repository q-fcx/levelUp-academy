package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.ProRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

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
        User user = new User(null, moderatorDTO.getUsername(), moderatorDTO.getPassword(), moderatorDTO.getEmail(), moderatorDTO.getFirstName(), moderatorDTO.getLastName(), moderatorDTO.getRole(), null,null,null,null, LocalDate.now());
        Moderator moderator = new Moderator(null,user);
        authRepository.save(user);
        moderatorRepository.save(moderator);
    }
    public void updateModerator(Integer id, ModeratorDTO moderatorDTO){
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        User user = moderator.getUser();
        user.setUsername(moderatorDTO.getUsername());
        user.setPassword(moderatorDTO.getPassword());
        user.setEmail(moderatorDTO.getEmail());
        user.setFirstName(moderatorDTO.getFirstName());
        user.setLastName(moderatorDTO.getLastName());

        authRepository.save(user);
    }
    public void deleteModerator(Integer id){
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        authRepository.delete(moderator.getUser());
        moderatorRepository.delete(moderator);
    }





}
