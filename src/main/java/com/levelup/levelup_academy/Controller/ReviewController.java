package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.Review;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("get-all")
    public ResponseEntity getAllReviews(@AuthenticationPrincipal User moderator) {
        return ResponseEntity.status(200).body(reviewService.getAllReviews(moderator.getId()));
    }

    @GetMapping("get-my-reviews")
    public ResponseEntity getMyReviews(@AuthenticationPrincipal User trainer) {
        return ResponseEntity.status(200).body(reviewService.getMyReviews(trainer.getId()));
    }

    @PostMapping("/add/{sessionId}")
    public ResponseEntity addReview(@AuthenticationPrincipal User user, @RequestBody @Valid Review review, @PathVariable Integer sessionId) {
        reviewService.addReview(user.getId(),review, sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Review Added successfully"));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity deleteReview(@AuthenticationPrincipal User user, @PathVariable Integer reviewId) {
        reviewService.deleteReview(user.getId(), reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }
}
