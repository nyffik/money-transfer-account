package com.mokaz.bankaccount.domain;

public interface ApplicationEventPublisher {
    void publishEvent(DomainEvent domainEvent);
}
