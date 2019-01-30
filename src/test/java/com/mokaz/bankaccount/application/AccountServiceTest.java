package com.mokaz.bankaccount.application;

import com.mokaz.bankaccount.domain.Account;
import com.mokaz.bankaccount.domain.AccountNotFoundException;
import com.mokaz.bankaccount.domain.AggregateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private AccountService accountService;
    @Mock
    UIDGenerator uidGenerator;

    @Mock
    AggregateService aggregateService;
    @Captor
    ArgumentCaptor<Account> accountArgumentCaptor;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(aggregateService, uidGenerator);
    }

    @Test
    @DisplayName("Should create account 1")
    void shouldCreateAccount() {
        //Given
        when(uidGenerator.generate()).thenReturn("1");

        //When
        accountService.create("John", "account1");

        //Then
        verify(aggregateService).store(accountArgumentCaptor.capture());
        assertAccount(accountArgumentCaptor.getValue(), "John", "account1", "0");
    }

    private void assertAccount(Account result, String expectedOwnerName, String expectedName, String expectedAmount) {
        assertThat(result.getOwnerName()).isEqualTo(expectedOwnerName);
        assertThat(result.getName()).isEqualTo(expectedName);
        assertThat(result.getAmount()).isEqualTo(expectedAmount);
    }

    @Test
    @DisplayName("Should deposit to account 1")
    void shouldDeposit() {
        //Given
        Account account = initAccount("1", "John", "account1");
        when(aggregateService.load("1")).thenReturn(account);

        //When
        accountService.deposit("1", BigDecimal.valueOf(100));

        //Then
        verify(aggregateService).store(accountArgumentCaptor.capture());
        assertAccount(accountArgumentCaptor.getValue(), "John", "account1", "100");
    }

    private Account initAccount(String s, String john, String account12) {
        Account account = Account.from(s);
        account.create(john, account12);
        return account;
    }

    @Test
    @DisplayName("Should withdraw from account 1")
    void shouldWithdraw() {
        //Given
        Account account = createAccountWithDeposit("1", "John", "account1", 200);
        when(aggregateService.load("1")).thenReturn(account);

        //When
        accountService.withdraw("1", BigDecimal.valueOf(150));

        //Then
        verify(aggregateService).store(accountArgumentCaptor.capture());
        assertAccount(accountArgumentCaptor.getValue(), "John", "account1", "50");
    }

    @Test
    @DisplayName("Should transfer money from account 1 to account 2")
    void shouldTransferMoney() {
        //Given
        Account account = createAccountWithDeposit("1", "John", "account1", 300);
        doReturn(account).when(aggregateService).load("1");

        Account account2 = createAccountWithDeposit("2", "Mark", "account2", 200);
        doReturn(account2).when(aggregateService).load("2");

        //When
        accountService.transfer("1", "2", BigDecimal.valueOf(250));

        //Then
        verify(aggregateService, times(2)).store(accountArgumentCaptor.capture());
        assertAccount(accountArgumentCaptor.getAllValues().get(0), "John", "account1", "50");

        assertAccount(accountArgumentCaptor.getAllValues().get(1), "Mark", "account2", "450");

    }
    @Test
    @DisplayName("Should throw exception when account to not found during transfer")
    void shouldThrowException() {
        //Given
        Account account = createAccountWithDeposit("1", "John", "account1", 300);
        doReturn(account).when(aggregateService).load("1");

        doThrow(AccountNotFoundException.class).when(aggregateService).load("2");

        //When
        Throwable throwable = catchThrowable(()->accountService.transfer("1", "2", BigDecimal.valueOf(250)));

        //Then
        verify(aggregateService, never()).store(accountArgumentCaptor.capture());
        assertThat(throwable).isInstanceOf(AccountNotFoundException.class);
    }

    private Account createAccountWithDeposit(String aggregateId, String ownerName, String name, int amount) {
        Account account = initAccount(aggregateId, ownerName, name);
        account.deposit(BigDecimal.valueOf(amount));
        return account;
    }

}