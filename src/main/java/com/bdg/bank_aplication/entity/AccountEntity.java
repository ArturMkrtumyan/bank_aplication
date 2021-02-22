package com.bdg.bank_aplication.entity;

import com.bdg.bank_aplication.model_enam.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "accounts")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_number")
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private Type accountType;

    private Long balance;

    @Column(name = "created_date")
    private LocalDate createdDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity userEntity;

    @OneToMany(
            mappedBy = "accountEntity",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    Set<TransactionEntity> transactionEntitySet;

    public AccountEntity() {
    }

    public AccountEntity(Long accountNumber, Type accountType, Long balance, LocalDate createdDate, UserEntity userEntity, Set<TransactionEntity> transactionEntitySet) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.createdDate = createdDate;
        this.userEntity = userEntity;
        this.transactionEntitySet = transactionEntitySet;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Type getAccountType() {
        return accountType;
    }

    public void setAccountType(Type accountType) {
        this.accountType = accountType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Set<TransactionEntity> getTransactionEntitySet() {
        return transactionEntitySet;
    }

    public void setTransactionEntitySet(Set<TransactionEntity> transactionEntitySet) {
        this.transactionEntitySet = transactionEntitySet;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "accountId=" + accountId +
                ", accountNumber=" + accountNumber +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", createdDate=" + createdDate +
                '}';
    }
}