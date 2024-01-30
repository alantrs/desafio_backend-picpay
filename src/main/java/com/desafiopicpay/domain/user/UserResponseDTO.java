package com.desafiopicpay.domain.user;

import java.math.BigDecimal;

public record UserResponseDTO(
        String firstName,
        String lastName,
        String document,
        String email,
        BigDecimal balance,
        UserType userType
) {
}
