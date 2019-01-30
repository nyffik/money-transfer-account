package com.mokaz.bankaccount.domain;

class InsufficientAmountException extends RuntimeException{

    InsufficientAmountException(String message) {
        super(message);
    }
}
