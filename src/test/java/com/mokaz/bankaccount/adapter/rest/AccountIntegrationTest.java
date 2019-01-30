package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountQueryRepository;
import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class AccountIntegrationTest {
    @Inject
    AccountClient client;

    @Inject
    AccountQueryRepository accountQueryRepository;

    @AfterEach
    void clean() {
        accountQueryRepository.deleteAll();
    }

    @Test
    void should_create_account() {
        //Given
        CreateAccountDto createAccountDto = createAccountDto("account1", "John");

        //When
        client.create(createAccountDto);

        //Then
        List<AccountResource> accounts = client.findAll();
        assertThat(accounts.get(0).getName()).isEqualTo("account1");
        assertThat(accounts.get(0).getOwnerName()).isEqualTo("John");

    }

    @Test
    void should_deposit() {
        //Given
        DepositDto depositDto = initDepositDto();

        //When
        client.deposit(depositDto);

        //Then
        List<AccountResource> accountsAfterDeposit = client.findAll();
        verifyAmountAfterDeposit(accountsAfterDeposit);
    }

    @Test
    void should_transfer_money() {
        //Given
        TransferDto transferDto = initTransferDto();
        //When
        client.transfer(transferDto);

        //Then
        List<AccountResource> allAfterTransfer = client.findAll();
        assertThat(allAfterTransfer).hasSize(2).allMatch(ar -> ar.getAmount().equals(new BigDecimal("200")));
    }

    private CreateAccountDto createAccountDto(String accountName, String ownerName) {
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setAccountName(accountName);
        createAccountDto.setOwnerName(ownerName);
        return createAccountDto;
    }


    private void verifyAmountAfterDeposit(List<AccountResource> accountsAfterDeposit) {
        assertThat(accountsAfterDeposit).hasSize(1);
        assertThat(accountsAfterDeposit.get(0).getAmount()).isEqualTo("100");
    }

    private DepositDto initDepositDto() {
        initAccount("account1", "John");
        List<AccountResource> accounts = client.findAll();
        return createDepositDto(accounts, "100");
    }

    private void initAccount(String accountName, String accountOwner) {
        CreateAccountDto createAccountDto = createAccountDto(accountName, accountOwner);
        client.create(createAccountDto);
    }

    private DepositDto createDepositDto(List<AccountResource> accounts, String amount) {
        DepositDto depositDto = new DepositDto();
        depositDto.setAmount(amount);
        depositDto.setAggregateId(accounts.get(0).getAggregateId());
        return depositDto;
    }


    private TransferDto initTransferDto() {
        initAccount("account1", "john");
        initAccount("account2", "mark");

        List<AccountResource> all = client.findAll();
        assertThat(all).hasSize(2).allMatch(ar -> ar.getAmount().equals(BigDecimal.ZERO));


        DepositDto depositDto = createDepositDto(all, "400");
        client.deposit(depositDto);

        List<AccountResource> allAfterDeposit = client.findAll();
        assertThat(allAfterDeposit).hasSize(2).anyMatch(ar -> ar.getAggregateId().equals(all.get(0).getAggregateId()) && ar.getAmount().equals(new BigDecimal(
                "400")));

        return createTransferDto(allAfterDeposit);
    }

    private TransferDto createTransferDto(List<AccountResource> all) {
        TransferDto transferDto = new TransferDto();
        transferDto.setAggregateIdFrom(all.get(0).getAggregateId());
        transferDto.setAggregateIdTo(all.get(1).getAggregateId());
        transferDto.setAmount("200");
        return transferDto;
    }


}