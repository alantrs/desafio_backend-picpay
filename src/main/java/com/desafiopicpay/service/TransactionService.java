package com.desafiopicpay.service;

import com.desafiopicpay.domain.transaction.Transaction;
import com.desafiopicpay.domain.transaction.TransactionalRequestDTO;
import com.desafiopicpay.domain.user.User;
import com.desafiopicpay.domain.user.UserRequestDTO;
import com.desafiopicpay.repositories.TransactionalRepository;
import com.desafiopicpay.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionalRepository transactionalRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final NotificationService notificationService;

    public TransactionService(TransactionalRepository transactionalRepository, UserService userService,
                              ModelMapper modelMapper, RestTemplate restTemplate,
                              NotificationService notificationService,
                              UserRepository userRepository){
        this.transactionalRepository = transactionalRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    public void createTransaction(TransactionalRequestDTO request) throws Exception {
        User sender = modelMapper.map(userService.findUserById(request.getSenderId()), User.class);
        User receiver = modelMapper.map(userService.findUserById(request.getReceiverId()), User.class);
        userService.validateTransaction(sender, request.getValue());

        Boolean isAuthorized = this.authorizeTransactional(sender, request.getValue());
        if(!isAuthorized){
            throw new Exception();
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getValue());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(request.getValue()));
        receiver.setBalance(receiver.getBalance().add(request.getValue()));

        transactionalRepository.save(transaction);
        userRepository.save(sender);
        userRepository.save(receiver);

        notificationService.SendNotification(sender, "Transação realizada com sucesso");
        notificationService.SendNotification(receiver, "Transação recebida com sucesso");
    }
    public Boolean authorizeTransactional(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } return false;
    }
}
