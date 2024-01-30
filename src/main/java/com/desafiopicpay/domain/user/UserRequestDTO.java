package com.desafiopicpay.domain.user;

import java.math.BigDecimal;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String document,
        String email,
        String password,
        BigDecimal balance,
        UserType userType) {

}
