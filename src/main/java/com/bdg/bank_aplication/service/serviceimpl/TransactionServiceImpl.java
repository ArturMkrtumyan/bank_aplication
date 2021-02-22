package com.bdg.bank_aplication.service.serviceimpl;

import com.bdg.bank_aplication.entity.AccountEntity;
import com.bdg.bank_aplication.entity.TransactionEntity;
import com.bdg.bank_aplication.entity.UserEntity;
import com.bdg.bank_aplication.model_enam.Status;
import com.bdg.bank_aplication.model_enam.Type;
import com.bdg.bank_aplication.repo.AccountRepo;
import com.bdg.bank_aplication.repo.TransactionRepo;
import com.bdg.bank_aplication.repo.UserRepo;
import com.bdg.bank_aplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;
    private final UserRepo userRepo;

    @Autowired
    public TransactionServiceImpl(TransactionRepo transactionRepo, AccountRepo accountRepo, UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Optional<TransactionEntity> getById(long id) {
        return transactionRepo.findById(id);
    }

    @Override
    public List<TransactionEntity> getAll() {
        return transactionRepo.findAll();
    }

    @Override
    public TransactionEntity save(TransactionEntity transactionEntity) {
        if (transactionEntity.getStatus() == null) {
            transactionEntity.setStatus(Status.PENDING);
        }
        if (transactionEntity.getType() == null) {
            try {
                throw new Exception("It is required type of transaction.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (transactionEntity.getAccountEntity() == null) {
            try {
                throw new Exception("It is required account of transaction.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (transactionEntity.getUserEntity() == null) {
            try {
                throw new Exception("It is required to mention user that make a transaction.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        transactionRepo.save(transactionEntity);
        return transactionEntity;
    }

    @Override
    public TransactionEntity update(TransactionEntity transactionEntity) {
        return transactionRepo.save(transactionEntity);
    }

    @Override
    public List<TransactionEntity> getUserTransactions(UserEntity userEntity) {
        return null;
    }

    @Override
    public List<TransactionEntity> seeAllTransactionById(Long userId) {
        List<TransactionEntity> transactionEntity = transactionRepo.findAll();
        List<TransactionEntity> transactionEntities = new ArrayList<>();
        for (TransactionEntity transaction : transactionEntity) {
            if (transaction.getUserEntity().getUserId().equals(userId)) {
                transactionEntities.add(transaction);
            }
        }
        return transactionEntities;
    }

    @Override
    public ResponseEntity<?> createTransaction(TransactionEntity transactionEntity, Long userId, Long accountId) {
        transactionEntity.setTransactionDate(LocalDate.now());
        Optional<AccountEntity> accountEntity = accountRepo.findById(accountId);
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        AccountEntity accountEntityOne = new AccountEntity();
        UserEntity userEntityOne = new UserEntity();
        if (!accountEntity.isPresent()) {
            try {
                throw new Exception("Account with such id does not exists");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "Account with such id does not exists",
                    HttpStatus.BAD_REQUEST);
        }
        if (!userEntity.isPresent()) {
            try {
                throw new Exception("User with such id does not exists");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "User with such id does not exists",
                    HttpStatus.BAD_REQUEST);
        }
        userEntityOne = userEntity.get();
        accountEntityOne = accountEntity.get();
        if (transactionEntity.getStatus() == Status.ACCEPTED && transactionEntity.getType() == Type.DEPOSIT) {
            accountEntityOne.setBalance(accountEntityOne.getBalance() + transactionEntity.getAmount());
        }
        if (transactionEntity.getStatus() == Status.ACCEPTED && transactionEntity.getType() == Type.WITHDRAWAL) {
            if (accountEntityOne.getBalance() >= transactionEntity.getAmount()) {
                accountEntityOne.setBalance(accountEntityOne.getBalance() - transactionEntity.getAmount());
            } else {
                try {
                    throw new Exception(" Balance is less than the amount");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return new ResponseEntity<>(
                        "Balance is less than the amount",
                        HttpStatus.BAD_REQUEST);
            }
        }
        transactionEntity.setUserEntity(userEntityOne);
        transactionEntity.setAccountEntity(accountEntityOne);
        this.save(transactionEntity);
        return ResponseEntity.ok(transactionEntity);
    }

    @Override
    public ResponseEntity<?> cancelTransaction(Long transactionId) {
        Optional<TransactionEntity> transactionEntity = transactionRepo.findById(transactionId);
        if (transactionEntity.isPresent()) {
            if (transactionEntity.get().getStatus() == Status.CANCELED) {
                try {
                    throw new Exception("Transaction with such id already canceled");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return new ResponseEntity<>(
                        "Transaction with such id already canceled",
                        HttpStatus.BAD_REQUEST);
            }
            TransactionEntity transactionEntityOne = transactionEntity.get();
            if (transactionEntity.get().getStatus() == Status.PENDING) {
                transactionEntityOne.setStatus(Status.CANCELED);
                this.update(transactionEntityOne);
            }
        } else {
            try {
                throw new Exception("Transaction with such id does not exist");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "Transaction with such id does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(transactionEntity);
    }

    @Override
    public List<TransactionEntity> getByDate(Long userId, String date) {
        Optional<UserEntity> userEntity = userRepo.findById(userId);
        if (!userEntity.isPresent()) {
            try {
                throw new Exception("User with such email exists.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        List<TransactionEntity> transactionEntities = this.seeAllTransactionById(userId);
        List<TransactionEntity> transactionEntitiesTemp = new ArrayList<>();
        if (transactionEntities != null) {
            for (TransactionEntity entity : transactionEntities) {
                if (entity.getTransactionDate().isEqual(LocalDate.parse(date))) {
                    transactionEntitiesTemp.add(entity);
                }
                return transactionEntitiesTemp;
            }
        } else {
            try {
                throw new Exception("Transaction not exist");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
