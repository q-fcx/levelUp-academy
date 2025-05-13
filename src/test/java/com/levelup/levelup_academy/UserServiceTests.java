package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.SubscriptionRepository;
import com.levelup.levelup_academy.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    AuthRepository authRepository;

    @Mock
    ModeratorRepository moderatorRepository;

    @Mock
    SubscriptionRepository subscriptionRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void testGenerateModeratorLogin_Success() {
        // Arrange
        Integer adminId = 1;
        Integer moderatorId = 2;

        User admin = new User();
        admin.setId(adminId);
        admin.setRole("ADMIN");

        Moderator moderator = new Moderator();
        moderator.setId(moderatorId);

        when(authRepository.findUserById(adminId)).thenReturn(admin);
        when(moderatorRepository.findModeratorById(moderatorId)).thenReturn(moderator);

        // Act
        assertDoesNotThrow(() -> userService.generateModeratorLogin(adminId, moderatorId));

        // Assert
        verify(authRepository, times(1)).save(any(User.class));
        verify(moderatorRepository, times(1)).save(moderator);
    }

    @Test
    public void testGenerateModeratorLogin_AdminNotFoundOrNotAdmin() {
        Integer adminId = 1;
        Integer moderatorId = 2;

        User notAdmin = new User();
        notAdmin.setId(adminId);
        notAdmin.setRole("PLAYER"); // not admin

        when(authRepository.findUserById(adminId)).thenReturn(notAdmin);

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.generateModeratorLogin(adminId, moderatorId);
        });

        assertEquals("only admin can create moderator account", exception.getMessage());
    }

    @Test
    public void testGenerateModeratorLogin_ModeratorNotFound() {
        Integer adminId = 1;
        Integer moderatorId = 2;

        User admin = new User();
        admin.setId(adminId);
        admin.setRole("ADMIN");

        when(authRepository.findUserById(adminId)).thenReturn(admin);
        when(moderatorRepository.findModeratorById(moderatorId)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            userService.generateModeratorLogin(adminId, moderatorId);
        });

        assertEquals("Moderator not found", exception.getMessage());
    }
}
