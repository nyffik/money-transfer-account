package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.Subscribe;
import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.application.AccountResource;
import com.mokaz.bankaccount.domain.WithdrawCreatedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class WithdrawAcountEventSubscirber {
    private final AccountQueryRepository accountQueryRepository;

    @Subscribe
    void handle(WithdrawCreatedEvent event) {

        AccountResource accountResource = accountQueryRepository.findByAggregateId(event.getAggregateId());
        accountResource.withdraw(event.getAmount());
        accountQueryRepository.store(accountResource);
    }
}
