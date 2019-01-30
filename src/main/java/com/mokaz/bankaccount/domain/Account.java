package com.mokaz.bankaccount.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private List<DomainEvent> pendingEvents =  new ArrayList<>();

    private final  String aggregateId;
    private String ownerName;
    private String name;
    private BigDecimal amount;

    public static Account from(String aggregateId) {
        return Account.from(aggregateId, new ArrayList<>());
    }

    public static Account from(String aggregateId, List<DomainEvent> events) {
        Account note = new Account(aggregateId);
        events.forEach(note::apply);
        return note;
    }

    public void create(String ownerName, String name) {
        add(new AccountCreatedEvent(aggregateId, LocalDateTime.now(), ownerName, name));
    }

    private void add(DomainEvent accountCreatedEvent) {
        pendingEvents.add(accountCreatedEvent);
        apply(accountCreatedEvent);
    }

    private void apply(DomainEvent event) {
        if(event instanceof AccountCreatedEvent) {
            apply((AccountCreatedEvent)event);
        } else if (event instanceof DepositCreatedEvent) {
            apply((DepositCreatedEvent)event);
        } else if(event instanceof WitdrawCreatedEvent) {
            apply((WitdrawCreatedEvent)event);
        }

    }

    private void apply(AccountCreatedEvent accountCreatedEvent) {
        this.ownerName = accountCreatedEvent.getOwnerName();
        this.name = accountCreatedEvent.getAccountName();
        this.amount = BigDecimal.ZERO;
    }

    public void deposit(BigDecimal amount) {
        add(new DepositCreatedEvent(aggregateId, LocalDateTime.now(), amount));

    }

    private void apply(DepositCreatedEvent depositCreatedEvent) {
        this.amount = amount.add(depositCreatedEvent.getAmount());
    }

    public void withdraw(BigDecimal amount) {
        if(this.amount.compareTo(amount)<0) {
            throw new InsuffiecientAmountException("Insufficient amount on account");
        }
        add(new WitdrawCreatedEvent(aggregateId, LocalDateTime.now(), amount));
    }

    private void apply(WitdrawCreatedEvent witdrawCreatedEvent) {
        amount = amount.subtract(witdrawCreatedEvent.getAmount());
    }

    public void clearEvents(){
        pendingEvents.clear();
    }

}
