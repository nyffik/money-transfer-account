package com.mokaz.bankaccount.adapter.db.query;

import com.mokaz.bankaccount.application.AccountQueryRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
class QueryFactory {

    @Singleton
    @Bean
    AccountQueryRepository accountQueryRepository(){
        return new AccountQueryRepositoryImpl();
    }
}
