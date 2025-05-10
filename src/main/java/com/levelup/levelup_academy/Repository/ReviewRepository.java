package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Review;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);

    Review findReviewByUserAndTrainer(User user, Trainer trainer);

}
