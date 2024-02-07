package com.desafiopicpay.domain.user;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRequestDTO{
    private String firstName;
    private String lastName;
    private String document;
    private String email;
    private String password;
    private BigDecimal balance;
    private UserType userType;
}
