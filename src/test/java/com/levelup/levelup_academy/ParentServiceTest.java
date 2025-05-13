package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTOOut.ParentDTOOut;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Parent;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.*;
import com.levelup.levelup_academy.Service.EmailNotificationService;
import com.levelup.levelup_academy.Service.ParentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParentServiceTest {
    @Mock
    ParentRepository parentRepository;

    @Mock
    AuthRepository authRepository;

    @Mock
    ChildRepository childRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    StatisticChildRepository statisticChildRepository;

    @Mock
    ModeratorRepository moderatorRepository;

    @Mock
    EmailNotificationService emailNotificationService;

    @InjectMocks
    ParentService parentService;

    @Test
    public void testGetAllParents_Success() {
        // Arrange
        Integer moderatorId = 1;
        Moderator moderator = new Moderator();
        when(moderatorRepository.findModeratorById(moderatorId)).thenReturn(moderator);

        // Parent user
        User parentUser = new User();
        parentUser.setUsername("parent1");
        parentUser.setFirstName("Fatima");
        parentUser.setLastName("Ali");
        parentUser.setEmail("fatima@example.com");

        // Child user
        User childUser = new User();
        childUser.setUsername("child1");
        childUser.setFirstName("Sara");
        childUser.setLastName("Ali");
        childUser.setEmail("sara@example.com");

        // Actual parent entity
        Parent parent = new Parent();
        parent.setUser(parentUser);

        // Mock child
        Child child = mock(Child.class);
        Parent childParent = mock(Parent.class); // mock parent for the child

        when(child.getParent()).thenReturn(childParent);
        when(childParent.getUser()).thenReturn(childUser);

        parent.setChildren(List.of(child));
        when(parentRepository.findAll()).thenReturn(List.of(parent));

        // Act
        List<ParentDTOOut> result = parentService.getAllParents(moderatorId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("parent1", result.get(0).getUsername());
        assertEquals(1, result.get(0).getChildren().size());
        assertEquals("child1", result.get(0).getChildren().get(0).getUsername());
    }

    @Test
    public void testGetAllParents_ModeratorNotFound() {
        when(moderatorRepository.findModeratorById(1)).thenReturn(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            parentService.getAllParents(1);
        });

        assertEquals("Moderator not found", exception.getMessage());
    }
}
