package com.bdg.bank_aplication.service.serviceimpl;

import com.bdg.bank_aplication.entity.UserEntity;
import com.bdg.bank_aplication.model_enam.Role;
import com.bdg.bank_aplication.repo.UserRepo;
import com.bdg.bank_aplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<UserEntity> getById(long id) {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        return userEntity;
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepo.findAll();
    }

    @Override
    public ResponseEntity<?> save(UserEntity userEntity) {
        if (userRepo.existsByEmail(userEntity.getEmail())) {
            try {
                throw new Exception("User with such email exists.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "User with such email exists.",
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByPassword(userEntity.getPassword())) {
            try {
                throw new Exception("User with such password exists.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "User with such password exists.",
                    HttpStatus.BAD_REQUEST);
        }
            userEntity.setRole(Role.USER);
            userRepo.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @Override
    public void delete(long id) {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (userEntity.isPresent()) {
            userRepo.deleteById(id);
        }
    }

    @Override
    public ResponseEntity<?> updateUserRole(UserEntity userEntity, Long userId, Long adminId) {
        Optional<UserEntity> admin = this.getById(adminId);
        UserEntity userUpdated;
        if(admin.isPresent()){
            if(admin.get().getRole()==Role.ADMIN){
                Optional<UserEntity> user = this.getById(userId);
                if(user.isPresent()){
                    userUpdated=user.get();
                      userUpdated.setRole(userEntity.getRole());
                      this.updateUser(userUpdated);
                      userUpdated=this.getById(userId).get();
                }else {
                    try {
                        throw new Exception("User with such id doesn't exists.");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return new ResponseEntity<>(
                            "User with such id doesn't exists.",
                            HttpStatus.BAD_REQUEST);
                }

            }else {
                try {
                    throw new Exception("User with not admin role cannot change other user role.");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return new ResponseEntity<>(
                        "User with not admin role cannot change other user",
                        HttpStatus.BAD_REQUEST);
            }
        }else {
            try {
                throw new Exception("Admin with such id does not exits.");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return new ResponseEntity<>(
                    "Admin with such id does not exits.",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userUpdated);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Boolean existsByPassword(String password) {
        return userRepo.existsByPassword(password);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
       return userRepo.save(userEntity);
    }
}
