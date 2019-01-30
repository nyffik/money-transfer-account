package com.mokaz.bankaccount.adapter.storage;

import com.mokaz.bankaccount.domain.DomainEvent;
import com.mokaz.bankaccount.domain.DomainEventStoreRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DomainEventStoreRepositoryImpl implements DomainEventStoreRepository {

    private List<DomainEvent> events = new ArrayList<>();

    @Override
    public List<DomainEvent> findByAggregateIdOrderByCreatedAt(String aggregateId) {
        return events.stream()
                .filter(de->de.getAggregateId().equals(aggregateId))
                .sorted(Comparator.comparing(DomainEvent::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Override
    public void save(DomainEvent domainEvent) {
        events.add(domainEvent);
    }
}
