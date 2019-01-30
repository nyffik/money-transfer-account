package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("/account")
public interface AccountClient {

    @Get
    List<AccountResource> findAll();

    @Post
    HttpResponse create(@Body CreateAccountDto createAccountDto);
}
