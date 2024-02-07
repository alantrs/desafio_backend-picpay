package com.desafiopicpay.domain.user;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserResponseDTO {
    private String firstName;
    private String lastName;
    private String document;
    private String email;
    private BigDecimal balance;
    private UserType userType;
}
