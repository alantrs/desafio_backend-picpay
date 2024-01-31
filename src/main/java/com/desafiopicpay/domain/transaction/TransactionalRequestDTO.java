package com.desafiopicpay.domain.transaction;

import com.desafiopicpay.domain.user.User;

import java.math.BigDecimal;

public record TransactionalRequestDTO(
        BigDecimal value,
        Long senderId,
        Long receiverId) {
}
