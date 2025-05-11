package com.levelup.levelup_academy.Service;


import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {


    private final AuthRepository authRepository;
    private final ModeratorRepository moderatorRepository;

    public void register(User user) {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPassword(user.getPassword());

        user1.setRole("ADMIN");
        authRepository.save(user1);

    }

    public void generateModeratorLogin(Integer adminId, Integer moderatorId) {
        User admin = authRepository.findUserById(adminId);
        if (admin == null || !admin.getRole().equals("ADMIN")) {
            throw new ApiException("only admin can create moderator account");
        }

        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if (moderator == null) {
            throw new ApiException("Moderator not found");
        }
        String username = "moderator" + moderatorId;
        Random random = new Random();
        String password = String.valueOf(random.nextInt(90000000) + 10000000);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        authRepository.save(user);
        moderatorRepository.save(moderator);
    }
}
