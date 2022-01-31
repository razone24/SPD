package com.rbalasa.transactionmaker.generator;

import com.rbalasa.transactionmaker.concurrent.TransactionRunnable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionGenerator {

    @Value("${user.save.uri}")
    private String userSaveUri;

    @Value("${account.save.uri}")
    private String accountSaveUri;

    @Value("${transaction.save.uri}")
    private String transactionSaveUri;

    @Value("${pool.size}")
    private int poolSize;

    @Value("${account.no}")
    private int accountsNo;

    @Value("${transaction.no}")
    private int transactionNo;

    private final RestOperations restOperations;

    @Scheduled(cron = "0/60 * * * * ?")
    public void compute() {
        System.out.println(poolSize);
        System.out.println(accountsNo);
        System.out.println(transactionNo);
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(poolSize);
        executor.schedule(new TransactionRunnable(userSaveUri, accountSaveUri, transactionSaveUri, restOperations, accountsNo, transactionNo), 1, TimeUnit.MINUTES);
    }
}
