package com.desafiopicpay.service;

import com.desafiopicpay.domain.transaction.TransactionalRequestDTO;
import com.desafiopicpay.domain.user.User;
import com.desafiopicpay.repositories.TransactionalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionalRepository transactionalRepository;
    private final UserService userService;

    private ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    public TransactionService(TransactionalRepository transactionalRepository, UserService userService,
                              ModelMapper modelMapper, RestTemplate restTemplate){
        this.transactionalRepository = transactionalRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    public void createTransaction(TransactionalRequestDTO request) throws Exception {
        User sender = modelMapper.map(userService.findUserById(request.senderId()), User.class);
        User receiver = modelMapper.map(userService.findUserById(request.receiverId()), User.class);

        userService.validateTransaction(sender, request.value());


    }
    public Boolean authorizeTransactional(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } return false;
    }
}
