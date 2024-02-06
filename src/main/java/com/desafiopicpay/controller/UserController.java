package com.desafiopicpay.controller;

import com.desafiopicpay.domain.user.UserRequestDTO;
import com.desafiopicpay.domain.user.UserResponseDTO;
import com.desafiopicpay.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequest){
        UserResponseDTO userResponse = this.userService.saveUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
