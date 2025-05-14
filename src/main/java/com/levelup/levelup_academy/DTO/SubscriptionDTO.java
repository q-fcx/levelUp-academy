package com.levelup.levelup_academy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDTO {
    private String name;
    private String number;
    private int month;
    private int year;
    private int cvc;
    private String currency = "SAR"; // default
    private String callbackUrl = "http://localhost:8080/api/v1/payments/callback";
}
