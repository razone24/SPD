package com.rbalasa.accountancyapp.controller;

import com.rbalasa.accountancyapp.model.Transaction;
import com.rbalasa.accountancyapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/save")
    @CrossOrigin("http://localhost:9090")
    public ResponseEntity add(@RequestBody Transaction transaction) {
        Transaction addedTransaction = transactionService.add(transaction);

        if(addedTransaction != null) {
            return ResponseEntity.ok(addedTransaction);
        } else {
            return ResponseEntity.badRequest().body("BAD REQUEST!");
        }
    }
}
