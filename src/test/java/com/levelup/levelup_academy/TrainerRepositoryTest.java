package com.levelup.levelup_academy;


import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private AuthRepository authRepository;

    @Test
    @Order(1)
    public void testFindTrainerById() {

        User user = new User();
        user.setUsername("trainer123");
        user.setPassword("password");
        user.setEmail("trainer@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole("TRAINER");

        authRepository.save(user);

        // Create and save a trainer
        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainerRepository.save(trainer);


        Trainer foundTrainer = trainerRepository.findTrainerById(trainer.getId());


        assertNotNull(foundTrainer);
        assertEquals(user.getUsername(), foundTrainer.getUser().getUsername());
    }

}