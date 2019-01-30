package com.mokaz.bankaccount.adapter.storage;

import com.mokaz.bankaccount.domain.DomainEventStoreRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
class EventStoreFactory {
    @Singleton
    @Bean
    DomainEventStoreRepository domainEventStoreRepository(){
        return new DomainEventStoreRepositoryImpl();
    }
}
