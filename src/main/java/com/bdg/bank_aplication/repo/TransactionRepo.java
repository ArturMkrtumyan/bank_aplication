package com.bdg.bank_aplication.repo;

import com.bdg.bank_aplication.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity,Long> {
    List<TransactionEntity> getAllByUserEntity(Long userId);

}
