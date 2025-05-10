package com.levelup.levelup_academy.Service;


import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final AuthRepository authRepository;

    public void register(User user){
         User user1 = new User();
         user1.setUsername(user.getUsername());
         user1.setFirstName(user.getFirstName());
         user1.setLastName(user.getLastName());
         user1.setPassword(user.getPassword());

        user1.setRole("ADMIN");
        authRepository.save(user1);

    }


}
