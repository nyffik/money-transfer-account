package com.mokaz.bankaccount.application;

import com.mokaz.bankaccount.domain.AggregateService;
import com.mokaz.bankaccount.domain.ApplicationEventPublisher;
import com.mokaz.bankaccount.domain.DomainEventStoreRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.UUID;

@Factory
public class AccountFactory {

    @Singleton
    @Bean
    AccountService accountService(AggregateService aggregateService){
        return new AccountService(aggregateService,()-> UUID.randomUUID().toString());
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

}
