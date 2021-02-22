package com.bdg.bank_aplication.controller;

import com.bdg.bank_aplication.entity.TransactionEntity;
import com.bdg.bank_aplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;

    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionEntity transactionEntity, @RequestParam(name = "userId") Long userId, @RequestParam(name = "accountId") Long accountId) {
        return transactionService.createTransaction(transactionEntity, userId, accountId);
    }

    @PatchMapping("/{transactionId}/cancel")
    public ResponseEntity<?> cancelTransaction(@PathVariable(name = "transactionId") Long transactionId) {
        return transactionService.cancelTransaction(transactionId);
    }

    @GetMapping("{userId}/all")
    public List<TransactionEntity> seeAllTransactionById(@PathVariable("userId") Long userId) {
        return transactionService.seeAllTransactionById(userId);
    }

    @GetMapping
    public List<TransactionEntity> findAllTransaction() {
        return transactionService.getAll();
    }

    @GetMapping("/filter")
    public List<TransactionEntity> getTransactionByDate(@RequestParam(name = "userId") Long userId, @RequestParam(name = "date") String date) {
        return transactionService.getByDate(userId, date);
    }
}
