package com.bdg.bank_aplication.controller;

import com.bdg.bank_aplication.entity.AccountEntity;
import com.bdg.bank_aplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountEntity accountEntity, @RequestParam(name = "userId") Long userId, @RequestParam(name = "adminId") Long adminId) {
        return accountService.createAccount(accountEntity, userId, adminId);
    }

}
