package com.mokaz.bankaccount.adapter.db.query;

import com.google.common.collect.ImmutableList;
import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.application.AccountResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class AccountQueryRepositoryImpl implements AccountQueryRepository {
    Map<String,AccountResource> accounts = new HashMap<>();

    @Override
    public AccountResource findByAggregateId(String aggregateId) {
        return accounts.get(aggregateId);
    }

    @Override
    public void store(AccountResource resource) {
        accounts.put(resource.getAggregateId(),resource);
    }

    @Override
    public List<AccountResource> findAll() {
        return ImmutableList.copyOf(accounts.entrySet().stream().map(e->e.getValue()).collect(Collectors.toList()));
    }
}
