package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Controller.ReviewController;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private User user;
    private Trainer trainer;
    private Moderator moderator;
    private Session session;
    private Review review;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setEmail("john@example.com");

        trainer = new Trainer();
        trainer.setId(1);
        trainer.setUser(user);

        moderator = new Moderator();
        moderator.setId(1);

        session = new Session();
        session.setId(1);
        session.setTrainer(trainer);

        review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setTrainer(trainer);
        review.setSession(session);
        review.setTitle("Great session");
        review.setComment("Very informative");
        review.setRate(5);
        review.setDate(LocalDate.now());
    }

    @Test
    void getAllReviews_ModeratorExists_ReturnsOk() throws Exception {
        when(reviewService.getAllReviews(1)).thenReturn(List.of(review));

        mockMvc.perform(get("/api/v1/review/get-all"))
                .andExpect(status().isOk());
    }
}