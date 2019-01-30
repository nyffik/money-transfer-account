package com.mokaz.bankaccount.adapter.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class TransferDto {
    String aggregateIdFrom;
    String aggregateIdTo;
    String amount;
}
