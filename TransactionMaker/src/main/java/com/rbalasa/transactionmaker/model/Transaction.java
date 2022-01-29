package com.rbalasa.transactionmaker.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    private Long id;

    private Boolean expense;

    private String description;

    private Double amount;

    private Date date;

    private Account userAccount;
}
