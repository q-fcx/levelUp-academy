package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private AuthRepository authRepository;

    private Player player1, player2;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "player1", "password", "player1@example.com", "John", "Doe", "PLAYER", LocalDate.now(), null, null, null, null, null, null, null, null);
        player1 = new Player(null, user, null, null);
        player2 = new Player(null, new User(null, "player2", "password", "player2@example.com", "Jane", "Doe", "PLAYER", LocalDate.now(), null, null, null, null, null, null, null, null), null, null);
    }

    @Test
    public void findPlayerByIdTest() {

        authRepository.save(player1.getUser());
        playerRepository.save(player1);

        Player player = playerRepository.findPlayerById(player1.getId());
        Assertions.assertThat(player).isEqualTo(player1);
    }


}
