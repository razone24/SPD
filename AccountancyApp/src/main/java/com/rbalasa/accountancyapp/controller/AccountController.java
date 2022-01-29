package com.rbalasa.accountancyapp.controller;

import com.rbalasa.accountancyapp.model.Account;
import com.rbalasa.accountancyapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/save")
    @CrossOrigin("http://localhost:9090")
    public ResponseEntity store(@RequestBody Account account) {
        Account storedAccount = accountService.store(account);

        if(storedAccount != null) {
            return ResponseEntity.ok(storedAccount);
        } else {
            return ResponseEntity.badRequest().body("BAD REQUEST!");
        }
    }
}
