package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// comment
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String name;
    private String number;
    private int cvc;
    private int month;
    private int year;

    @JsonIgnore
    private double amount;
    private String currency;
    private String description;
    private String callbackUrl;
}