package com.mokaz.bankaccount.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class AccountResource {
    @NonNull
    private final  String aggregateId;
    private final String ownerName;
    private final String name;
    private BigDecimal amount;

    @JsonCreator
    public AccountResource(@JsonProperty("aggregateId") @NonNull String aggregateId,@JsonProperty("ownerName") String ownerName,@JsonProperty("name") String name,@JsonProperty("amount") BigDecimal amount) {
        this.aggregateId = aggregateId;
        this.ownerName = ownerName;
        this.name = name;
        this.amount = amount;
    }

    public void deposit(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }
}
