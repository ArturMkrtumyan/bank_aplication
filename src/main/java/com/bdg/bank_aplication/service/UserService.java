package com.bdg.bank_aplication.service;

import com.bdg.bank_aplication.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> getById(long id);

    List<UserEntity> getAll();

    ResponseEntity<?> save(UserEntity userEntity);

    void delete(long id);

    ResponseEntity<?> updateUserRole(UserEntity userEntity, Long userId, Long adminId);

    Boolean existsByEmail(String email);

    Boolean existsByPassword(String password);

    UserEntity updateUser(UserEntity userEntity);

}
