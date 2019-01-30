package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.Subscribe;
import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.application.AccountResource;
import com.mokaz.bankaccount.domain.AccountCreatedEvent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreatedAccountEventSubscriber {

    private final AccountQueryRepository accountQueryRepository;

    @Subscribe
    public void handle(AccountCreatedEvent event) {
        AccountResource build = AccountResource.builder()
                .aggregateId(event.getAggregateId())
                .ownerName(event.getOwnerName())
                .name(event.getAccountName())
                .amount(BigDecimal.ZERO)
                .build();
        accountQueryRepository.store(build);
    }
}
