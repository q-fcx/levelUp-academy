//package com.levelup.levelup_academy;
//
//
//
//import com.levelup.levelup_academy.Model.Pro;
//import com.levelup.levelup_academy.Model.User;
//import com.levelup.levelup_academy.Repository.AuthRepository;
//import com.levelup.levelup_academy.Repository.ProRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class ProRepositoryTest {
//
//    @Autowired
//    private ProRepository proRepository;
//
//    @Autowired
//    private AuthRepository userRepository;
//
//    @Test
//    @Order(1)
//    public void testFindProById() {
//        User user = new User();
//        user.setUsername("AbdullahAli01");
//        user.setPassword("password123");
//        user.setEmail("AbdullahAli01@gmail.com");
//        user.setFirstName("Abdullah");
//        user.setLastName("Ali");
//        user.setRole("PRO");
//
//        Pro pro = new Pro();
//        pro.setCvPath("cv1.pdf");
//        pro.setIsApproved(true);
//        pro.setUser(user);
//        user.setPro(pro);
//
//        userRepository.save(user);
//
//        Pro foundPro = proRepository.findProById(pro.getId());
//        assertNotNull(foundPro);
//        assertEquals("cv1.pdf", foundPro.getCvPath());
//    }
//
//    @Test
//    @Order(2)
//    public void testFindByIsApproved() {
//        // Setup test data
//        User user = new User();
//        user.setUsername("Ali01");
//        user.setPassword("AliPassword1");
//        user.setEmail("Ali01@gmail.com");
//        user.setFirstName("Pro");
//        user.setLastName("Two");
//        user.setRole("PRO");
//
//        Pro pro = new Pro();
//        pro.setCvPath("cv2.pdf");
//        pro.setIsApproved(false);
//        pro.setUser(user);
//        user.setPro(pro);
//
//        userRepository.save(user);
//
//        List<Pro> unapprovedPros = proRepository.findByIsApproved(false);
//        assertFalse(unapprovedPros.isEmpty());
//
//        boolean found = unapprovedPros.stream()
//                .anyMatch(p -> p.getCvPath().equals("cv2.pdf"));
//        assertTrue(found);
//    }
//}
