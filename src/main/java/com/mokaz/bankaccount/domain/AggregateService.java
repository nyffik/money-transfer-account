package com.mokaz.bankaccount.domain;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AggregateService {

    private final DomainEventStoreRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void store(Account aggregate) {

        List<DomainEvent> pendingEvents = aggregate.getPendingEvents();

        pendingEvents.forEach(this::saveAndPublish);
        aggregate.clearEvents();

    }

    public Account load(String aggregateId) {
        List<DomainEvent> events = repository.findByAggregateIdOrderByCreatedAt(aggregateId);
        return Account.from(aggregateId,events);
    }

    private void saveAndPublish(DomainEvent domainEvent) {
        repository.save(domainEvent);
        applicationEventPublisher.publishEvent(domainEvent);
    }
}
