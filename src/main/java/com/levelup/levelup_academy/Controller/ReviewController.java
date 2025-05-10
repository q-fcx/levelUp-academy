package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.Review;
import com.levelup.levelup_academy.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("get-all")
    public ResponseEntity getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @PostMapping("/add/{userId}/{sessionId}")
    public ResponseEntity addReview(@RequestBody @Valid Review review, @PathVariable Integer userId, @PathVariable Integer sessionId) {
        reviewService.addReview(review, userId, sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Review Added successfully"));
    }

    @DeleteMapping("/delete/{userId}/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Integer userId, @PathVariable Integer reviewId) {
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }
}
