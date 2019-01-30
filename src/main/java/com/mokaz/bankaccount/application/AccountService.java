package com.mokaz.bankaccount.application;

import com.mokaz.bankaccount.domain.Account;
import com.mokaz.bankaccount.domain.AggregateService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AccountService {
    private final AggregateService aggregateService;
    private final UIDGenerator uidGenerator;

    public void create(String ownerName, String name) {
        Account account = Account.from(uidGenerator.generate());
        account.create(ownerName,name);
        store(account);
    }

    private void store(Account account){
        aggregateService.store(account);
    }

    public Account load(String aggregateId) {
        return aggregateService.load(aggregateId);
    }

    public void deposit(String aggregateIdTo, BigDecimal amount) {
        Account account = load(aggregateIdTo);
        account.deposit(amount);
        store(account);

    }

    public void withdraw(String aggregateId, BigDecimal amount) {
        Account account = load(aggregateId);
        account.withdraw(amount);
        store(account);
    }

    public void transfer(String aggregateIdFrom, String aggregateIdTo, BigDecimal amount) {
        Account load = load(aggregateIdFrom);
        load.withdraw(amount);
        store(load);

        Account load2 = load(aggregateIdTo);
        load2.deposit(amount);
        store(load2);

    }
}
