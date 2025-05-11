package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("get-all")
    public ResponseEntity getAllSubscriptions() {
        return ResponseEntity.status(200).body(subscriptionService.getAllSubscriptions());
    }

    @PostMapping("/basic/{userId}")
    public ResponseEntity addBasicSubscription(@PathVariable Integer userId) {
        subscriptionService.basicSubscription(userId);
        return ResponseEntity.status(200).body(new ApiResponse("you subscribed to Basic Subscription"));
    }

    @PostMapping("/standard/{userId}")
    public ResponseEntity addStandardSubscription(@PathVariable Integer userId) {
        subscriptionService.standardSubscription(userId);
        return ResponseEntity.status(200).body(new ApiResponse("you subscribed to Standard Subscription"));
    }

    @PostMapping("/premium/{userId}")
    public ResponseEntity addPremiumSubscription(@PathVariable Integer userId) {
        subscriptionService.premiumSubscription(userId);
        return ResponseEntity.status(200).body(new ApiResponse("you subscribed to Premium Subscription"));
    }


}
