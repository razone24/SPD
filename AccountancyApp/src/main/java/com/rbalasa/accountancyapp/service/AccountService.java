package com.rbalasa.accountancyapp.service;

import com.rbalasa.accountancyapp.model.Account;
import com.rbalasa.accountancyapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final AccountRepository accountRepository;

    public Account store(Account account) {
        if (account != null) {
            return accountRepository.save(account);
        }
        return null;
    }
}
