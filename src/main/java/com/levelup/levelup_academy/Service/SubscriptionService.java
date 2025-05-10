package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Model.Subscription;
import com.levelup.levelup_academy.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions(){
        return subscriptionRepository.findAll();
    }

    public void addSubscription(Subscription subscription) {
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.from(LocalDate.now()).plusDays(30));

        if(subscription.getPackageType().equals("BASIC")) {
            subscription.setSessionCount(4);
        }
    }


}
