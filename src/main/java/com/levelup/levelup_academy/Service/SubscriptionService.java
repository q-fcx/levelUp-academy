package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Subscription;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final AuthRepository authRepository;


    public void basicSubscription(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Subscription subscription = new Subscription();
        subscription.setPackageType("BASIC");
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setSessionCount(4);
        subscription.setPrice(200);
        subscription.setUser(user);

        authRepository.save(user);
        subscriptionRepository.save(subscription);

    }

    public void standardSubscription(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Subscription subscription = new Subscription();
        subscription.setPackageType("STANDARD");
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setSessionCount(8);
        subscription.setPrice(300);
        subscription.setUser(user);

        authRepository.save(user);
        subscriptionRepository.save(subscription);
    }

    public void premiumSubscription(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Subscription subscription = new Subscription();
        subscription.setPackageType("PREMIUM");
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setSessionCount(12);
        subscription.setPrice(600);
        subscription.setUser(user);

        authRepository.save(user);
        subscriptionRepository.save(subscription);
    }


}
