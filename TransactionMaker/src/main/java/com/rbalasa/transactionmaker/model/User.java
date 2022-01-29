package com.rbalasa.transactionmaker.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long id;

    private String name;

    private String email;

    private List<Account> accounts;
}
