package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;
    private final SessionRepository sessionRepository;
    private final TrainerRepository trainerRepository;
    private final EmailNotificationService emailNotificationService;
    private final ModeratorRepository moderatorRepository;


    public List<Review> getAllReviews(Integer moderatorId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        return reviewRepository.findAll();
    }

    public Set<Review> getMyReviews(Integer trainerId) {
        Trainer trainer = trainerRepository.findTrainerById(trainerId);
        if(trainer == null) throw new ApiException("Trainer not found");
        return trainer.getReviews();
    }

    public void addReview(Integer userId, Review review, Integer sessionId) {
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

        String trainerEmail = trainer.getUser().getEmail();
        String message = "<html><body style='font-family: Arial, sans-serif; color: #fff; background-color: #A53A10; padding: 40px 20px;'>"
                + "<div style='max-width: 600px; margin: auto; background: rgba(255, 255, 255, 0.05); border-radius: 12px; padding: 20px; text-align: center;'>"
                + "<img src='https://i.imgur.com/Q6FtCEu.jpeg' alt='LevelUp Academy Logo' style='width:90px; border-radius: 10px; margin-bottom: 20px;'/>"
                + "<h2>🌟 You Got a New Review!</h2>"
                + "<p style='font-size: 16px;'>Hi <strong>" + trainer.getUser().getFirstName() + "</strong>,</p>"
                + "<p style='font-size: 16px;'>You just received a new review from <strong>" + user.getFirstName() + "</strong>:</p>"
                + "<p style='font-size: 16px;'><strong>Title:</strong> " + review.getTitle() + "<br>"
                + "<strong>Rating:</strong> " + review.getRate() + "/5<br>"
                + "<strong>Comment:</strong> " + review.getComment() + "</p>"
                + "<p style='font-size: 14px;'>Keep up the great work!<br>– The LevelUp Academy Team</p>"
                + "</div></body></html>";

        EmailRequest email = new EmailRequest();
        email.setRecipient(trainerEmail);
        email.setSubject("🌟 New Review Received on LevelUp Academy");
        email.setMessage(message);
        emailNotificationService.sendEmail(email);

        authRepository.save(user);
        sessionRepository.save(session);
        trainerRepository.save(trainer);

    }

    public void deleteReview(Integer userId, Integer reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);
        if(review == null) throw new ApiException("Review not found");

        if(!review.getUser().getId().equals(userId)) throw new ApiException("You have no permission to delete this review");
    }
}
