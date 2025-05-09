package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Subscription;
import com.levelup.levelup_academy.Service.SubscriptionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findSubscriptionById(Integer id);
}
