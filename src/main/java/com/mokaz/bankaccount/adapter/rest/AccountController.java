package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Controller("/account")
@RequiredArgsConstructor
class AccountController {

    private final AccountService accountService;

    @Post(consumes = MediaType.APPLICATION_JSON)
     void create(@Body CreateAccountDto createAccountDto){
        accountService.create(createAccountDto.getOwnerName(),createAccountDto.getAccountName());
    }

    @Post(value="/deposit", consumes = MediaType.APPLICATION_JSON)
     void deposit(@Body DepositDto depositDto){
        accountService.deposit(depositDto.getAggregateId(), new BigDecimal(depositDto.getAmount()));
    }

    @Post(value = "/transfer",consumes = MediaType.APPLICATION_JSON)
     void transfer(@Body TransferDto transferDto){
        accountService.transfer(transferDto.getAggregateIfFrom(),transferDto.getAggregateIdTo(), new BigDecimal(transferDto.getAmount()));
    }
}
