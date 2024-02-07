package com.desafiopicpay.domain.transaction;

import com.desafiopicpay.domain.user.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionalRequestDTO{
        private BigDecimal value;
        private Long senderId;
        private Long receiverId;
}
