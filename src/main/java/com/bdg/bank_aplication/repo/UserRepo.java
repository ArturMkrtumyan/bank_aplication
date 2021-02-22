package com.bdg.bank_aplication.repo;

import com.bdg.bank_aplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {
    Boolean existsByEmail(String email);

    Boolean existsByPassword(String password);
}
