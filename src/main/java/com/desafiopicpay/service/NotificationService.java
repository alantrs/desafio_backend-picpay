package com.desafiopicpay.service;

import com.desafiopicpay.domain.notification.NotificationRequestDto;
import com.desafiopicpay.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public void SendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationRequestDto notificationRequest = new NotificationRequestDto(email, message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", notificationRequest, String.class);

        if (!notificationResponse.getStatusCode().equals(HttpStatus.OK)){
            throw new Exception();
        }
    }
}
