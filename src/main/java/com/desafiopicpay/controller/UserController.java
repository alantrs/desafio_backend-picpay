package com.desafiopicpay.controller;

import com.desafiopicpay.domain.user.User;
import com.desafiopicpay.domain.user.UserRequestDTO;
import com.desafiopicpay.domain.user.UserResponseDTO;
import com.desafiopicpay.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "USERS")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Save user")
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserRequestDTO userRequest){
        UserResponseDTO userResponse = this.userService.saveUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List users")
    public ResponseEntity<List<UserResponseDTO>> listUser(){
        return new ResponseEntity<>(userService.userList(), HttpStatus.OK);
    }
}
