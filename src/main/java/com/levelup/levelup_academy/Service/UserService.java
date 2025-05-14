package com.levelup.levelup_academy.Service;


import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Subscription;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {


    private final AuthRepository authRepository;
    private final ModeratorRepository moderatorRepository;
    private final SubscriptionRepository subscriptionRepository;

    public void register(User user) {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPassword(user.getPassword());

        user1.setRole("ADMIN");
        authRepository.save(user1);

    }

    public List<Subscription> getAllSubscriptions(Integer adminId) {
        User admin = authRepository.findUserById(adminId);
        if(admin == null) throw new ApiException("Admin not found");
        return subscriptionRepository.findAll();
    }

    public void generateModeratorLogin(Integer adminId, Integer moderatorId) {
        User admin = authRepository.findUserById(adminId);
        if (admin == null || !admin.getRole().equals("ADMIN")) {
            throw new ApiException("Only admin can create moderator account");
        }

        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if (moderator == null) {
            throw new ApiException("Moderator not found");
        }

        String username = "moderator" + moderatorId;
        String password = String.valueOf(new Random().nextInt(90000000) + 10000000);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("MODERATOR");
        user.setModerator(moderator);

        authRepository.save(user);
    }


    //Register Moderator
    public void registerModerator(Integer adminId, ModeratorDTO moderatorDTO){
        User admin = authRepository.findUserById(adminId);
        if(admin == null) throw new ApiException("Admin not found");
        moderatorDTO.setRole("MODERATOR");
        String hashPassword = new BCryptPasswordEncoder().encode(moderatorDTO.getPassword());
        User user = new User(null, moderatorDTO.getUsername(),hashPassword, moderatorDTO.getEmail(), moderatorDTO.getFirstName(), moderatorDTO.getLastName(), moderatorDTO.getRole(), LocalDate.now(),null,null,null,null,null,null,null,null);
        Moderator moderator = new Moderator(null,user,null);
        authRepository.save(user);
        moderatorRepository.save(moderator);

    }
}
