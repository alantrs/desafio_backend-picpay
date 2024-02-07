package com.desafiopicpay.domain.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequestDto{

    private String email;
    private String message;
}
