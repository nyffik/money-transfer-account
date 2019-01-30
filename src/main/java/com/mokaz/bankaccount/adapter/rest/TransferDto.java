package com.mokaz.bankaccount.adapter.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDto {
    String aggregateIfFrom;
    String aggregateIdTo;
    String amount;
}
