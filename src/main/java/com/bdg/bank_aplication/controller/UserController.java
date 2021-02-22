package com.bdg.bank_aplication.controller;

import com.bdg.bank_aplication.entity.UserEntity;
import com.bdg.bank_aplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity) {
        return userService.save(userEntity);
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<?> updateUserRole(@RequestBody UserEntity userEntity, @PathVariable Long userId, @RequestParam("adminId") Long adminId) {
        return userService.updateUserRole(userEntity, userId, adminId);
    }

    @GetMapping("all")
    public List<UserEntity> findAllUser() {
        return userService.getAll();
    }
}
