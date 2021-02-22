package com.bdg.bank_aplication.service;

import com.bdg.bank_aplication.entity.AccountEntity;
import com.bdg.bank_aplication.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountService {
    ResponseEntity<?> createAccount(AccountEntity accountEntity, Long userId, Long adminId);

    Optional<AccountEntity> getById(long id);

    Set<AccountEntity> getAll();

    AccountEntity save(AccountEntity accountEntity);

    void delete(long id);

    List<AccountEntity> getUserAccounts(Long userId);

    AccountEntity getAccountByAccountIdAndUser(Long accountId, UserEntity userEntity);

    AccountEntity update(AccountEntity accountEntity);
}
