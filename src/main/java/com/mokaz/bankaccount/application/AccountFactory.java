package com.mokaz.bankaccount.application;

import com.mokaz.bankaccount.domain.AggregateService;
import com.mokaz.bankaccount.domain.ApplicationEventPublisher;
import com.mokaz.bankaccount.domain.DomainEventStoreRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.UUID;

@Factory
class AccountFactory {

    @Singleton
    @Bean
    AccountService accountService(AggregateService aggregateService, UIDGenerator uidGenerator){
        return new AccountService(aggregateService,uidGenerator);
    }

    @Singleton
    @Bean
    AggregateService aggregateService(DomainEventStoreRepository repository, ApplicationEventPublisher applicationEventPublisher){
        return new AggregateService(repository, applicationEventPublisher);
    }

    @Singleton
    @Bean
    AccountQueryService accountQueryService(AccountQueryRepository accountQueryRepository){
        return new AccountQueryService(accountQueryRepository);
    }

    @Singleton
    @Bean
    UIDGenerator uidGenerator(){
        return ()-> UUID.randomUUID().toString();
    }

}
