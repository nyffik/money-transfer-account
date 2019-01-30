package com.mokaz.bankaccount.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter

public class DepositCreatedEvent extends DomainEvent {
    private final BigDecimal amount;
    public DepositCreatedEvent(String aggregateId, LocalDateTime now, BigDecimal amount) {
        super(aggregateId,now);
        this.amount = amount;
    }
}
