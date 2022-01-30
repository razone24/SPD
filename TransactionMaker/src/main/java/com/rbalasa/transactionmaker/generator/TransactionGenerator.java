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

    public static final int POOL_SIZE = 3;
    private final RestOperations restOperations;

    @Scheduled(cron = "0/60 * * * * ?")
    public void compute() {
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(POOL_SIZE);
        executor.schedule(new TransactionRunnable(userSaveUri, accountSaveUri, transactionSaveUri, restOperations), 1, TimeUnit.MINUTES);
    }
}
