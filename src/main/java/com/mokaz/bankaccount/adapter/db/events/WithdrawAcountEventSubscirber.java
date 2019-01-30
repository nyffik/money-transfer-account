package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.Subscribe;
import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.application.AccountResource;
import com.mokaz.bankaccount.domain.WitdrawCreatedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawAcountEventSubscirber {
    private final AccountQueryRepository accountQueryRepository;

    @Subscribe
    public void handle(WitdrawCreatedEvent event) {

        AccountResource accountResource = accountQueryRepository.findByAggregateId(event.getAggregateId());
        accountResource.withdraw(event.getAmount());
        accountQueryRepository.store(accountResource);
    }
}
