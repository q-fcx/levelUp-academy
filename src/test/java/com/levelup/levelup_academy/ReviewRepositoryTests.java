package com.levelup.levelup_academy;


import com.levelup.levelup_academy.Model.Review;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.ReviewRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private User user;
    private Trainer trainer;
    private Review review;

    @BeforeEach
    void setUp() {
        // إنشاء المستخدم بدون UserRepository
        user = new User(null, "kh111", "112233449", "K@outlook.com","Khadija", "Ismail", "PLAYER", LocalDate.now(),null,null,null,null,null,null,null,null);

        entityManager.persist(user);

        // حفظ المدرب باستخدام الريبو
        trainer = new Trainer();
        trainerRepository.save(trainer);

        entityManager.flush();

        // إنشاء وحفظ الريفيو
        review = new Review();
        review.setTitle("Great!");
        review.setComment("Trainer was awesome");
        review.setRate(5);
        review.setDate(LocalDate.now());
        review.setTrainer(trainer);
        review.setUser(user);

        reviewRepository.save(review);
    }

    @Test
    void testFindReviewById() {
        Review found = reviewRepository.findReviewById(review.getId());
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Great!");
    }

    @Test
    void testFindReviewByUserAndTrainer() {
        Review found = reviewRepository.findReviewByUserAndTrainer(user, trainer);
        assertThat(found).isNotNull();
        assertThat(found.getComment()).contains("awesome");
    }
}
