package com.rbalasa.transactionmaker.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private Long id;

    private String accountNo;

    private User user;

    private List<Transaction> transactions;
}
