package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Model.PaymentRequest;
import com.levelup.levelup_academy.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/card")
    public ResponseEntity<ResponseEntity<String>> processPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok().body(paymentService.processPayment(paymentRequest));
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity getPaymentStatus(@PathVariable String id) {
        return ResponseEntity.ok().body(paymentService.getPaymentStatus(id));
    }

    @GetMapping("callback")
    public ResponseEntity getCallback() {
        return ResponseEntity.ok().body(paymentService.callback());
    }
}
