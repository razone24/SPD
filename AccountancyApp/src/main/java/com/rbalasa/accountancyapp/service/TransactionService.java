package com.rbalasa.accountancyapp.service;

import com.rbalasa.accountancyapp.model.Transaction;
import com.rbalasa.accountancyapp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction add(Transaction transaction) {
        if(transaction != null) {
            return transactionRepository.save(transaction);
        }
        return null;
    }
}
