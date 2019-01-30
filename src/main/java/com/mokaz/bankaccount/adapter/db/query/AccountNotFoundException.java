package com.mokaz.bankaccount.adapter.db.query;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
