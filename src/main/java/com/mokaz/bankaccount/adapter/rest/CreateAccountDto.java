package com.mokaz.bankaccount.adapter.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class CreateAccountDto {

    private String ownerName;
    private String accountName;
}
