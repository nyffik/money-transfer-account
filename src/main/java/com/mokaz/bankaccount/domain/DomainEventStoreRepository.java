package com.mokaz.bankaccount.domain;

import java.util.List;

public interface DomainEventStoreRepository {
    List<DomainEvent> findByAggregateIdOrderByCreatedAt(String aggregateId);

    void save(DomainEvent domainEvent);
}
