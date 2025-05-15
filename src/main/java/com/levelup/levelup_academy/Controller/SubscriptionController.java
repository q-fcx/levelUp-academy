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

    @PostMapping("/basic")
    public ResponseEntity basicSubscription(@AuthenticationPrincipal User user, @RequestBody PaymentRequest paymentRequest) {
        return subscriptionService.basicSubscription(user.getId(), paymentRequest);
    }

@PostMapping("/standard")
public ResponseEntity standardSubscription(@AuthenticationPrincipal User user, @RequestBody PaymentRequest paymentRequest) {
    return subscriptionService.standardSubscription(user.getId(), paymentRequest);
}

@PostMapping("/premium")
public ResponseEntity premiumSubscription(@AuthenticationPrincipal User user, @RequestBody PaymentRequest paymentRequest) {
    return subscriptionService.premiumSubscription(user.getId(), paymentRequest);
}

//    @PostMapping("/subscribe/{userId}")
//    public ResponseEntity subscribeWithPayment(@PathVariable Integer userId,@RequestParam String packageType, @RequestBody PaymentRequest paymentRequest) {
//        return subscriptionService.subscribeWithPayment(userId, packageType, paymentRequest);
//    }
    @GetMapping("/get-subs")
    public ResponseEntity gatAllSubs(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(subscriptionService.gatAllSubs(user.getId()));
    }


}
