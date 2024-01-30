package com.desafiopicpay.service;

import com.desafiopicpay.domain.user.User;
import com.desafiopicpay.domain.user.UserRequestDTO;
import com.desafiopicpay.domain.user.UserResponseDTO;
import com.desafiopicpay.domain.user.UserType;
import com.desafiopicpay.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuario do tipo logista não esta autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public UserResponseDTO findUserById(Long id) throws Exception {
        UserResponseDTO responseDTO = modelMapper.map(userRepository.findById(id).orElseThrow(() -> new Exception("Usuario não encontrado")), UserResponseDTO.class);
        return responseDTO;
    }

    public void saveUser(UserRequestDTO request){
        User user = modelMapper.map(request, User.class);
        userRepository.save(user);
    }
}
