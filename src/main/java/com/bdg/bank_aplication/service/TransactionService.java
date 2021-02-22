package com.bdg.bank_aplication.service;

import com.bdg.bank_aplication.entity.TransactionEntity;
import com.bdg.bank_aplication.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Optional<TransactionEntity> getById(long id);

    List<TransactionEntity> getAll();

    TransactionEntity save(TransactionEntity transactionEntity);

    TransactionEntity update(TransactionEntity transactionEntity);

    List<TransactionEntity> getUserTransactions(UserEntity userEntity);

    List<TransactionEntity> seeAllTransactionById(Long userId);

    ResponseEntity<?> createTransaction(TransactionEntity transactionEntity, Long userId, Long accountId);

    ResponseEntity<?> cancelTransaction(Long transactionId);

    List<TransactionEntity> getByDate(Long userId, String date);
}
