package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountQueryService;
import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("/account")
@RequiredArgsConstructor
class AccountQueryController {

    private final AccountQueryService accountQueryService;

    @Get
    List<AccountResource> findAll() {
        return accountQueryService.findAll();
    }

    @Get("/{id}")
    AccountResource findById(String id) {
        return accountQueryService.load(id);
    }
}
