package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountResource;
import com.mokaz.bankaccount.application.UIDGenerator;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.MicronautJunit5Extension;
import io.reactivex.Flowable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class AccountControllerTest {
    @Inject
    AccountClient client;

    @Test
    void should_create_account_and_transfer_money(){
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setAccountName("account1");
        createAccountDto.setOwnerName("john");

        CreateAccountDto createAccountDto2 = new CreateAccountDto();
        createAccountDto2.setAccountName("account2");
        createAccountDto2.setOwnerName("mark");

        client.create(createAccountDto);
        client.create(createAccountDto2);
        List<AccountResource> all = client.findAll();
        assertThat(all).hasSize(2).allMatch(ar->ar.getAmount().equals(BigDecimal.ZERO));


        DepositDto depositDto = new DepositDto();
        depositDto.setAggregateId(all.get(0).getAggregateId());
        depositDto.setAmount("400");
        client.deposit(depositDto);

        List<AccountResource> allAfterDeposit = client.findAll();
        assertThat(allAfterDeposit).hasSize(2).anyMatch(ar -> ar.getAggregateId().equals(all.get(0).getAggregateId()) && ar.getAmount().equals(new BigDecimal(
                "400")));

        TransferDto transferDto = new TransferDto();
        transferDto.setAggregateIfFrom(all.get(0).getAggregateId());
        transferDto.setAggregateIdTo(all.get(1).getAggregateId());
        transferDto.setAmount("200");
        client.transfer(transferDto);

        List<AccountResource> allAfterTransfer = client.findAll();
        assertThat(allAfterTransfer).hasSize(2).allMatch(ar->ar.getAmount().equals(new BigDecimal("200")));
        ;
       // assertThat(all).hasSize(1);
    }

/*
    @MockBean
    UIDGenerator uidGenerator(){
        UIDGenerator uidGenerator = mock(UIDGenerator.class);
        when(uidGenerator.generate()).thenReturn("1").thenReturn("2");
        return uidGenerator;
    }
*/

}