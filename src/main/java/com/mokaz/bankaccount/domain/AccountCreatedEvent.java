package com.mokaz.bankaccount.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountCreatedEvent extends DomainEvent {

    private final String ownerName;
    private final String accountName;

    public AccountCreatedEvent(String aggregateId, LocalDateTime createdAt,String ownerName, String accountName) {
        super(aggregateId, createdAt);
        this.ownerName = ownerName;
        this.accountName = accountName;
    }
}
