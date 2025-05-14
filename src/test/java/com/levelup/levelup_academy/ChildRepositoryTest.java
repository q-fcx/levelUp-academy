package com.levelup.levelup_academy;
import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Repository.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ChildRepositoryTest {

    @Mock
    private ChildRepository childRepository;  // Mocking the repository

    private Child child;

    @BeforeEach
    void setUp() {
        child = new Child();
        child.setId(1);
        child.setFirstName("Ali");
        child.setLastName("Al-Hadi");
        // Set other properties if necessary
    }

    @Test
    void testFindChildById() {
        when(childRepository.findChildById(1)).thenReturn(child);
        Child foundChild = childRepository.findChildById(1);
        assertNotNull(foundChild, "Child should not be null");
        assertEquals(1, foundChild.getId(), "Child ID should match");
        assertEquals("Ali", foundChild.getFirstName(), "First name should match");
        assertEquals("Al-Hadi", foundChild.getLastName(), "Last name should match");
        verify(childRepository, times(1)).findChildById(1);
    }

    @Test
    void testFindChildById_NotFound() {
        when(childRepository.findChildById(99)).thenReturn(null);
        Child foundChild = childRepository.findChildById(99);
        assertNull(foundChild, "Child should be null when not found");
        verify(childRepository, times(1)).findChildById(99);
    }
}
