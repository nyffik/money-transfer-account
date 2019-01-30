package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.EventBus;
import com.mokaz.bankaccount.domain.ApplicationEventPublisher;
import com.mokaz.bankaccount.domain.DomainEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuavaEventPublisher implements ApplicationEventPublisher {

    private final EventBus eventBus;

    @Override
    public void publishEvent(DomainEvent domainEvent) {
        eventBus.post(domainEvent);
    }
}
