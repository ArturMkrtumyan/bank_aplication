package com.bdg.bank_aplication.service.serviceimpl;

import com.bdg.bank_aplication.entity.AccountEntity;
import com.bdg.bank_aplication.entity.UserEntity;
import com.bdg.bank_aplication.model_enam.Role;
import com.bdg.bank_aplication.repo.AccountRepo;
import com.bdg.bank_aplication.repo.UserRepo;
import com.bdg.bank_aplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private  final UserRepo userRepo;
     @Autowired
    public AccountServiceImpl(AccountRepo accountRepo,UserRepo userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo=userRepo;
    }

    @Override
    public ResponseEntity<?> createAccount(AccountEntity accountEntity,Long userId, Long adminId) {
        Optional<UserEntity> admin = userRepo.findById(adminId);
        if(admin.isPresent()&& admin.get().getRole()== Role.ADMIN){
           Optional<UserEntity>user=userRepo.findById(userId);
           if(user.isPresent()){
               accountEntity.setCreatedDate(LocalDate.now());
               accountEntity.setUserEntity(user.get());
               accountRepo.save(accountEntity);
           }else {
               try {
                   throw new Exception("User with such id does not exists.");
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               return new ResponseEntity<>(
                       "User with such id does not exists.",
                       HttpStatus.BAD_REQUEST);
           }
        }else {
            try {
                throw new Exception("Admin with such id does not exists.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "Admin with such id does not exists.",
                    HttpStatus.BAD_REQUEST);
        }
       return ResponseEntity.ok(accountEntity);
    }

    @Override
    public Optional<AccountEntity> getById(long id) {
        Optional<AccountEntity> accountEntity = this.accountRepo.findById(id);
        return accountEntity;
    }

    @Override
    public Set<AccountEntity> getAll() {
        Set<AccountEntity> accountEntities = new HashSet<>();
        Iterable<AccountEntity> accountEntitiesRepo = this.accountRepo.findAll();
        if (accountEntitiesRepo != null) {
            for (AccountEntity account : accountEntitiesRepo) {
                accountEntities.add(account);
            }
            return accountEntities;
        }
        return null;
    }

    @Override
    public AccountEntity save(AccountEntity accountEntity) {
       return accountRepo.save(accountEntity);
    }

    @Override
    public void delete(long id) {
        Optional<AccountEntity> accountEntity = accountRepo.findById(id);
        if (accountEntity.isPresent()) {
            accountRepo.deleteById(id);
        }
    }

    @Override
    public List<AccountEntity> getUserAccounts(Long userId) {
        return null;
    }

    @Override
    public AccountEntity getAccountByAccountIdAndUser(Long accountId, UserEntity userEntity) {
        return null;
    }

    @Override
    public AccountEntity update(AccountEntity accountEntity) {
        return null;
    }
}
