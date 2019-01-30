package com.mokaz.bankaccount.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DomainEvent {
    private final String aggregateId;
    private final LocalDateTime createdAt;
}

