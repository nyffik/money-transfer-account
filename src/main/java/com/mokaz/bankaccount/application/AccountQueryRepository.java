package com.mokaz.bankaccount.application;

import java.util.List;

public interface AccountQueryRepository {
    AccountResource findByAggregateId(String aggregateId);

    void store(AccountResource resource);

    List<AccountResource> findAll();
}
