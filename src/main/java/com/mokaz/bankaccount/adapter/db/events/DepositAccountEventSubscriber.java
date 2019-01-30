package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.Subscribe;
import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.application.AccountResource;
import com.mokaz.bankaccount.domain.DepositCreatedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DepositAccountEventSubscriber {
    private final AccountQueryRepository accountQueryRepository;

    @Subscribe
    public void handle(DepositCreatedEvent event) {

        AccountResource accountResource = accountQueryRepository.findByAggregateId(event.getAggregateId());
        accountResource.deposit(event.getAmount());
        accountQueryRepository.store(accountResource);
    }
}
