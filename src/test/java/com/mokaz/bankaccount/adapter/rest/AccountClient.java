package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("/account")
public interface AccountClient {

    @Get
    List<AccountResource> findAll();

    @Get("/{id}")
    AccountResource findById(String id);

    @Post
    void create(@Body CreateAccountDto createAccountDto);

    @Post("/deposit")
    void deposit(@Body DepositDto depositDto);

    @Post("/transfer")
    void transfer(@Body TransferDto transferDto);
}
