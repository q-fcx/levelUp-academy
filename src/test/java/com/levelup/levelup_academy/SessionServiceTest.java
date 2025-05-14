package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import com.levelup.levelup_academy.Service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private ModeratorRepository moderatorRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private BookingRepository bookingRepository;
//    @Mock
//    private EmailNotificationService emailNotificationService;

    @InjectMocks
    private SessionService sessionService;

    private Moderator moderator;
    private Trainer trainer;
    private Game game;
    private Session session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        moderator = new Moderator();
        trainer = new Trainer();
        game = new Game();
        session = new Session();

        // Mock common behavior
        when(moderatorRepository.findModeratorById(anyInt())).thenReturn(moderator);
        when(trainerRepository.findTrainerById(anyInt())).thenReturn(trainer);
        when(gameRepository.findGameById(anyInt())).thenReturn(game);
    }


    @Test
    void testAddClassWhenModeratorNotFound() {
        when(moderatorRepository.findModeratorById(anyInt())).thenReturn(null);

        ApiException thrown = assertThrows(ApiException.class, () -> {
            sessionService.addClass(1, session, 1, 1);
        });

        assertEquals("Moderator not found", thrown.getMessage());
    }
}
