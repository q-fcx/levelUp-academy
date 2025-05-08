package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findReviewByTrainer_Id(Integer trainerId);

}
