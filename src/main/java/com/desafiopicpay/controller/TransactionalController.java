package com.desafiopicpay.controller;

import com.desafiopicpay.domain.transaction.TransactionalRequestDTO;
import com.desafiopicpay.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionalController {

    private final TransactionService transactionService;

    public TransactionalController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create transaction")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionalRequestDTO transactionalRequest) throws Exception {
        transactionService.createTransaction(transactionalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
