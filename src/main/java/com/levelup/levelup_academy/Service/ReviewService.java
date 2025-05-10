package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Review;
import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ReviewRepository;
import com.levelup.levelup_academy.Repository.SessionRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;
    private final SessionRepository sessionRepository;
    private final TrainerRepository trainerRepository;


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Review review, Integer userId, Integer sessionId) {
        User user = authRepository.findUserById(userId);
        if(user == null) throw new ApiException("User not found");

        Session session = sessionRepository.findSessionById(sessionId);
        if(session == null) throw new ApiException("Session not found");

        Trainer trainer = trainerRepository.findTrainerById(session.getTrainer().getId());
        if(trainer == null) throw new ApiException("Trainer not found");

        Review review1 = reviewRepository.findReviewByUserAndTrainer(user, trainer);
        if(review1 != null) throw new ApiException("You already reviewed this trainer");

        Review rev = new Review();
        rev.setDate(LocalDate.now());
        rev.setUser(user);
        rev.setSession(session);
        rev.setTrainer(trainer);

        rev.setRate(review.getRate());
        rev.setComment(review.getComment());
        rev.setTitle(review.getTitle());
        reviewRepository.save(rev);


        authRepository.save(user);
        sessionRepository.save(session);
        trainerRepository.save(trainer);

    }
}
