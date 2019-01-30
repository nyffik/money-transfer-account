package com.mokaz.bankaccount.domain;

public class InsuffiecientAmountException extends RuntimeException{

    public InsuffiecientAmountException(String message) {
        super(message);
    }
}
