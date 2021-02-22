package com.bdg.bank_aplication.entity;

import com.bdg.bank_aplication.model_enam.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;

    private Integer age;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true

    )
    @JsonIgnore
    private Set<AccountEntity> accountEntitySet;
    @OneToMany(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<TransactionEntity> transactionEntitySet;

    public UserEntity() {
    }

    public UserEntity(Long userId, String name, Integer age, String email, String password, Role role) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Set<AccountEntity> getAccountEntitySet() {
        return accountEntitySet;
    }

    public Set<TransactionEntity> getTransactionEntitySet() {
        return transactionEntitySet;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}