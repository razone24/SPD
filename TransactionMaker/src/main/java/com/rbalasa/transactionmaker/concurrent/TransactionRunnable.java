package com.rbalasa.transactionmaker.concurrent;

import com.rbalasa.transactionmaker.model.Account;
import com.rbalasa.transactionmaker.model.Transaction;
import com.rbalasa.transactionmaker.model.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TransactionRunnable implements Runnable{

    private final String userSaveUri;
    private final String accountSaveUri;
    private final String transactionSaveUri;

    private final RestOperations restOperations;
    private final Integer accountsNo;
    private final Integer transactionNo;
    private final Random random = new Random();

    public TransactionRunnable(String userSaveUri, String accountSaveUri, String transactionSaveUri, RestOperations restOperations, Integer accountsNo, Integer transactionNo) {
        this.userSaveUri = userSaveUri;
        this.accountSaveUri = accountSaveUri;
        this.transactionSaveUri = transactionSaveUri;
        this.restOperations = restOperations;
        this.accountsNo = accountsNo;
        this.transactionNo = transactionNo;
    }

    @Override
    public void run() {
        generateData();
    }

    private void generateData() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        User user = User.builder()
                .name("User" + timestamp)
                .email("user" + timestamp + "@mail.com")
                .build();
        List<Account> accounts = new ArrayList<>();

        RequestEntity<User> requestEntity =
                new RequestEntity<>(user, HttpMethod.POST, URI.create(userSaveUri));
        ResponseEntity<User> savedUser = restOperations.exchange(requestEntity, User.class);
        System.out.println(savedUser);

        for (int i = 0; i < accountsNo; i++) {
            Account account = Account.builder()
                    .accountNo(timestamp + "-" + i)
                    .user(savedUser.getBody())
                    .build();
            RequestEntity<Account> accountRequestEntity =
                    new RequestEntity<>(account, HttpMethod.POST, URI.create(accountSaveUri));
            ResponseEntity<Account> savedAccount = restOperations.exchange(accountRequestEntity, Account.class);
            System.out.println(savedAccount);
            accounts.add(savedAccount.getBody());
        }

        for (int j = 0; j < transactionNo; j++) {
            Transaction transaction = Transaction.builder()
                    .amount(random.nextDouble() * 100)
                    .date(new Date())
                    .description("Transaction from date " + new Date())
                    .expense(random.nextBoolean())
                    .userAccount(accounts.get(random.nextInt(accountsNo)))
                    .build();
            RequestEntity<Transaction> transactionRequestEntity =
                    new RequestEntity<>(transaction, HttpMethod.POST, URI.create(transactionSaveUri));
            System.out.println(restOperations.exchange(transactionRequestEntity, Transaction.class));
        }
    }


}
