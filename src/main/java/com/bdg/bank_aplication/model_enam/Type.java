package com.bdg.bank_aplication.model_enam;

public enum Type {
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL");
    String type;
    Type(String type){
        this.type = type;
    }
}
