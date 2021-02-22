package com.bdg.bank_aplication.model_enam;

public enum Status {
    PENDING("PENDING"),
    CANCELED("CANCELED"),
    ACCEPTED("ACCEPTED");
    String status;

    Status(String status) {
        this.status = status;
    }
}
