package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Model.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";

    public ResponseEntity<String> processPayment(PaymentRequest paymentRequest) {

        String url = "https://api.moyasar.com/v1/payments";
        String callbackUrl = "http://localhost:8080/api/v1/payments/callback";

        String requestBody = String.format(
                "source[type]=creditcard&" +
                        "source[name]=%s&" +
                        "source[number]=%s&" +
                        "source[month]=%d&" +
                        "source[year]=%d&" +
                        "source[cvc]=%d&" +
                        "amount=%d&" +
                        "currency=%s&" +
                        "description=%s&" +
                        "callback_url=%s",
                paymentRequest.getName(),
                paymentRequest.getNumber(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                paymentRequest.getCvc(),
                (int)(paymentRequest.getAmount() * 100),
                paymentRequest.getCurrency(),
                paymentRequest.getDescription(),
                paymentRequest.getCallbackUrl()
        );


        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public String getPaymentStatus(String paymentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public String callback() {
        String message = "you paid successfully";
        return message;
    }


}
