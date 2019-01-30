package com.mokaz.bankaccount.application;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AccountQueryService {

    private final AccountQueryRepository accountQueryRepository;

    AccountResource load(String aggregateId) {
        return accountQueryRepository.findByAggregateId(aggregateId);
    }

   public List<AccountResource> findAll() {
        return accountQueryRepository.findAll();
    }
}
