package com.rbalasa.transactionmaker.generator;

import com.rbalasa.transactionmaker.model.Account;
import com.rbalasa.transactionmaker.model.Transaction;
import com.rbalasa.transactionmaker.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionGenerator {

    private final RestOperations restOperations;
    private final Integer ACCOUNTS_NO = 3;
    private final Integer TRANSACTION_NO = 60;
    private Random random = new Random();

    @Scheduled(cron = "0/1 * * * * ?")
    public void compute() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        User user = User.builder()
                .name("User" + timestamp)
                .email("user" + timestamp + "@mail.com")
                .build();
        List<Account> accounts = new ArrayList<>();

        RequestEntity<User> requestEntity =
                new RequestEntity<>(user, HttpMethod.POST, URI.create("http://localhost:8080/user/save"));
        ResponseEntity<User> savedUser = restOperations.exchange(requestEntity, User.class);
        System.out.println(savedUser);

        for (int i = 0; i < ACCOUNTS_NO; i++) {
            Account account = Account.builder()
                    .accountNo(timestamp + "-" + i)
                    .user(savedUser.getBody())
                    .build();
            RequestEntity<Account> accountRequestEntity =
                    new RequestEntity<>(account, HttpMethod.POST, URI.create("http://localhost:8080/account/save"));
            ResponseEntity<Account> savedAccount = restOperations.exchange(accountRequestEntity, Account.class);
            System.out.println(savedAccount);
            accounts.add(savedAccount.getBody());
        }

        for (int j = 0; j < TRANSACTION_NO; j++) {
            Transaction transaction = Transaction.builder()
                    .amount(random.nextDouble() * 100)
                    .date(new Date())
                    .description("Transaction from date " + new Date())
                    .expense(random.nextBoolean())
                    .userAccount(accounts.get(random.nextInt(ACCOUNTS_NO)))
                    .build();
            RequestEntity<Transaction> transactionRequestEntity =
                    new RequestEntity<>(transaction, HttpMethod.POST, URI.create("http://localhost:8080/transaction/save"));
            System.out.println(restOperations.exchange(transactionRequestEntity, Transaction.class));
        }
    }
}
