package com.mokaz.bankaccount.adapter.db.events;

import com.google.common.eventbus.EventBus;
import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.domain.ApplicationEventPublisher;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
class EventsFactory {


    @Singleton
    @Bean
    ApplicationEventPublisher applicationEventPublisher(EventBus eventBus){
        return new GuavaEventPublisher(eventBus);
    }

    @Singleton
    @Bean
    EventBus eventBus(AccountQueryRepository accountQueryRepository){
        EventBus eventBus = new EventBus();
        eventBus.register(new CreatedAccountEventSubscriber(accountQueryRepository));
        eventBus.register(new DepositAccountEventSubscriber(accountQueryRepository));
        eventBus.register(new WithdrawAcountEventSubscirber(accountQueryRepository));
        return eventBus;
    }
}
