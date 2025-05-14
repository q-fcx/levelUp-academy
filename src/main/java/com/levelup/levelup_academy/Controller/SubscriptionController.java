package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.PaymentRequest;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

//    @PostMapping("/basic")
//    public ResponseEntity addBasicSubscription(@AuthenticationPrincipal User userId) {
//        subscriptionService.basicSubscription(userId.getId());
//        return ResponseEntity.status(200).body(new ApiResponse("you subscribed to Basic Subscription"));
//    }
//
//    @PostMapping("/standard")
//    public ResponseEntity addStandardSubscription(@AuthenticationPrincipal User userId) {
//        subscriptionService.standardSubscription(userId.getId());
//        return ResponseEntity.status(200).body(new ApiResponse("you subscribed to Standard Subscription"));
//    }
//
//    @PostMapping("/premium")
//    public ResponseEntity addPremiumSubscription(@AuthenticationPrincipal User userId) {
//        subscriptionService.premiumSubscription(userId.getId());
//        return ResponseEntity.status(200).body(new ApiResponse("you subscribed to Premium Subscription"));
//    }

    @PostMapping("/subscribe/{userId}")
    public ResponseEntity subscribeWithPayment(@PathVariable Integer userId, @RequestParam String packageType, @RequestBody PaymentRequest paymentRequest) {
        return subscriptionService.subscribeWithPayment(userId, packageType, paymentRequest);
    }


}
