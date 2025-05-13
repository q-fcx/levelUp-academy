package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Game;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Repository.*;
import com.levelup.levelup_academy.Service.EmailNotificationService;
import com.levelup.levelup_academy.Service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @Mock
    SessionRepository sessionRepository;

    @Mock
    TrainerRepository trainerRepository;

    @Mock
    ModeratorRepository moderatorRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    EmailNotificationService emailNotificationService;

    @InjectMocks
    SessionService sessionService;

    @Test
    public void testAddClass_Success() {
        // Arrange
        Integer moderatorId = 1;
        Integer trainerId = 2;
        Integer gameId = 3;

        Session session = new Session();
        Moderator moderator = new Moderator();
        Trainer trainer = new Trainer();
        Game game = new Game();

        when(moderatorRepository.findModeratorById(moderatorId)).thenReturn(moderator);
        when(trainerRepository.findTrainerById(trainerId)).thenReturn(trainer);
        when(gameRepository.findGameById(gameId)).thenReturn(game);

        // Act
        sessionService.addClass(moderatorId, session, trainerId, gameId);

        // Assert
        assertEquals(trainer, session.getTrainer());
        assertEquals(game, session.getGame());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testAddClass_ModeratorNotFound() {
        when(moderatorRepository.findModeratorById(1)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            sessionService.addClass(1, new Session(), 2, 3);
        });

        assertEquals("Moderator not found", exception.getMessage());
    }

    @Test
    public void testAddClass_TrainerNotFound() {
        when(moderatorRepository.findModeratorById(1)).thenReturn(new Moderator());
        when(trainerRepository.findTrainerById(2)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            sessionService.addClass(1, new Session(), 2, 3);
        });

        assertEquals("Trainer not found", exception.getMessage());
    }

    @Test
    public void testAddClass_GameNotFound() {
        when(moderatorRepository.findModeratorById(1)).thenReturn(new Moderator());
        when(trainerRepository.findTrainerById(2)).thenReturn(new Trainer());
        when(gameRepository.findGameById(3)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            sessionService.addClass(1, new Session(), 2, 3);
        });

        assertEquals("Game not found", exception.getMessage());
    }
}
