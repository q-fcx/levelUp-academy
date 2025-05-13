package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Model.Parent;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ParentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private AuthRepository authRepository;

    private Integer savedId;

    @BeforeEach
    public void setUp() {
        // إنشاء مستخدم مرتبط بالـ Parent
        User user = new User();
        user.setUsername("parent_user");
        user.setFirstName("Amina");
        user.setLastName("Yousef");
        user.setEmail("amina@example.com");
        user.setPassword("password123");
        user.setRole("PARENTS");

        authRepository.save(user); // حفظ المستخدم يدويًا

        Parent parent = new Parent();
        parent.setUser(user);

        savedId = parentRepository.save(parent).getId();
    }

    @Test
    public void testFindParentById_Success() {
        Parent foundParent = parentRepository.findParentById(savedId);
        assertThat(foundParent).isNotNull();
        assertThat(foundParent.getUser().getUsername()).isEqualTo("parent_user");
    }

    @Test
    public void testFindParentById_NotFound() {
        Parent notFound = parentRepository.findParentById(999);
        assertThat(notFound).isNull();
    }
}
