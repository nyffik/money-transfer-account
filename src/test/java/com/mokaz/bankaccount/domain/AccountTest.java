package com.mokaz.bankaccount.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AccountTest {

    @Test
    void should_create_account() {
        //Given
        Account account = Account.from("1");

        //When
        account.create("John", "firstAccount");
        //Then

        assertThat(account.getAggregateId()).isEqualTo("1");
        assertThat(account.getOwnerName()).isEqualTo("John");
        assertThat(account.getName()).isEqualTo("firstAccount");
        assertThat(account.getAmount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void should_deposit_amount() {
        //Given
        Account account = Account.from("1");
        account.create("John","firstAccount");

        //When
        account.deposit(BigDecimal.valueOf(100));

        //Then
        assertThat(account.getAmount()).isEqualTo("100");
    }

    @Test
    void should_withdraw_amount() {
        //Given
        Account account = Account.from("1");
        account.create("John","firstAccount");
        account.deposit(BigDecimal.valueOf(100));

        //When
        account.withdraw(BigDecimal.valueOf(50));

        //Then
        assertThat(account.getAmount()).isEqualTo("50");
    }

    @Test
    void should_throw_exception_when_amount_is_less_than_withdraw() {
        //Given
        Account account = Account.from("1");
        account.create("John","firstAccount");
        account.deposit(BigDecimal.valueOf(100));

        //When
        Throwable throwable  = catchThrowable(()-> account.withdraw(BigDecimal.valueOf(150)));

        //Then
        assertThat(throwable).isInstanceOf(InsufficientAmountException.class)
                .hasMessage("Insufficient amount on account");
    }

}