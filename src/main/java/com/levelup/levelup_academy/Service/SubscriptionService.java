package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.SubscriptionDTO;
import com.levelup.levelup_academy.Model.PaymentRequest;
import com.levelup.levelup_academy.Model.Subscription;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final AuthRepository authRepository;
    private final PaymentService paymentService;


    public ResponseEntity basicSubscription(Integer userId, PaymentRequest paymentRequest) {
        User user = authRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setStatus("PENDING");
        subscription.setPackageType("BASIC");
        subscription.setSessionCount(4);
        subscription.setPrice(200);

        paymentRequest.setAmount(subscription.getPrice());
        paymentRequest.setDescription("Subscription: BASIC");
        paymentRequest.setCallbackUrl("http://localhost:8080/api/v1/payments/callback");

        ResponseEntity<String> response = paymentService.processPayment(paymentRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            subscription.setStatus("ACTIVE");
        }

        subscriptionRepository.save(subscription);
        return response;
    }

    public ResponseEntity standardSubscription(Integer userId, PaymentRequest paymentRequest) {
        User user = authRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setStatus("PENDING");
        subscription.setPackageType("STANDARD");
        subscription.setSessionCount(8);
        subscription.setPrice(300);

        paymentRequest.setAmount(subscription.getPrice());
        paymentRequest.setDescription("Subscription: STANDARD");
        paymentRequest.setCallbackUrl("http://localhost:8080/api/v1/payments/callback");

        ResponseEntity<String> response = paymentService.processPayment(paymentRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            subscription.setStatus("ACTIVE");
        }

        subscriptionRepository.save(subscription);
        return response;
    }

    public ResponseEntity premiumSubscription(Integer userId, PaymentRequest paymentRequest) {
        User user = authRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setStatus("PENDING");
        subscription.setPackageType("PREMIUM");
        subscription.setSessionCount(12);
        subscription.setPrice(600);

        paymentRequest.setAmount(subscription.getPrice());
        paymentRequest.setDescription("Subscription: PREMIUM");
        paymentRequest.setCallbackUrl("http://localhost:8080/api/v1/payments/callback");

        ResponseEntity<String> response = paymentService.processPayment(paymentRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            subscription.setStatus("ACTIVE");
        }

        subscriptionRepository.save(subscription);
        return response;
    }

//    public ResponseEntity subscribeWithPayment(Integer userId, String packageType, PaymentRequest paymentRequest) {
//        User user = authRepository.findUserById(userId);
//        if (user == null) throw new ApiException("User not found");
//
//        Subscription subscription = new Subscription();
//        subscription.setUser(user);
//        subscription.setStartDate(LocalDate.now());
//        subscription.setEndDate(LocalDate.now().plusDays(30));
//        subscription.setStatus("PENDING");
//
//        switch (packageType.toUpperCase()) {
//            case "BASIC":
//                subscription.setPackageType("BASIC");
//                subscription.setSessionCount(4);
//                subscription.setPrice(200);
//                break;
//            case "STANDARD":
//                subscription.setPackageType("STANDARD");
//                subscription.setSessionCount(8);
//                subscription.setPrice(300);
//                break;
//            case "PREMIUM":
//                subscription.setPackageType("PREMIUM");
//                subscription.setSessionCount(12);
//                subscription.setPrice(600);
//                break;
//            default:
//                throw new ApiException("Invalid package type");
//        }
//
//        paymentRequest.setAmount(subscription.getPrice());
//        paymentRequest.setDescription("Subscription: " + packageType);
//        paymentRequest.setCallbackUrl("http://localhost:8080/api/v1/payments/callback");
//
//        ResponseEntity<String> response = paymentService.processPayment(paymentRequest);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            subscription.setStatus("ACTIVE");
//        }
//
//        subscriptionRepository.save(subscription);
//        return response;
//    }
//
//
//    public List<Subscription> gatAllSubs(Integer userId){
//        User user = authRepository.findUserById(userId);
//        if (user == null){
//            throw new ApiException("User not found");
//        }
//        return subscriptionRepository.findAll();
//    }

}
