package com.bdg.bank_aplication.model_enam;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");
    String role;

    Role(String role) {
        this.role = role;
    }
}
