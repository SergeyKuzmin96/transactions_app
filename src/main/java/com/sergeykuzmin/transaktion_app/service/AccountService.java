package com.sergeykuzmin.transaktion_app.service;


import com.sergeykuzmin.transaktion_app.model.Account;
import com.sergeykuzmin.transaktion_app.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AccountService {


    @Autowired
    private AccountRepository repository;

    private final static Logger logger = LogManager.getLogger(AccountService.class.getName());


    public CompletableFuture<List<Account>> transact(Integer count) throws ExecutionException, InterruptedException {

        ArrayList<Account> accounts = (ArrayList<Account>) findAllAccounts();

        CompletableFuture[] arr = new CompletableFuture[count];

        for (int i = 0; i < count; i++) {

            final int index = i;

            arr[index] = CompletableFuture.runAsync(() -> {

                try {

                    AccountService.this.getTransact(accounts, index);
                } catch (ExecutionException e) {

                    logger.error(e.getMessage());
                } catch (InterruptedException e) {

                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            });
        }

        CompletableFuture.allOf(arr).join();

        saveAccounts(accounts);


        return CompletableFuture.completedFuture(accounts);

    }

    @Async
    public void getTransact(ArrayList<Account> accounts, int index) throws ExecutionException, InterruptedException {

        CompletableFuture.runAsync(() -> {

            logger.info("Start transact № " + index + " by " + Thread.currentThread().getName());

            sleep();

            int producerId = (int) (Math.random() * accounts.size());

            int consumerId;

            do {

                consumerId = (int) (Math.random() * accounts.size());

            } while (consumerId == producerId);

            int transfer = (int) (Math.random() * accounts.get(producerId).getAmount());

            accounts.get(producerId).setAmount(accounts.get(producerId).getAmount() - transfer);

            accounts.get(consumerId).setAmount(accounts.get(consumerId).getAmount() + transfer);

            logger.info("End transact № " + index + " by " + Thread.currentThread().getName());

        }).get();


    }

    private void sleep() {
        try {
            Thread.sleep((int) (Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void saveAccounts(Integer count, Integer amount) {

        Integer countD = 1;
        Integer amountD = 10000;

        if (count != null && count > 0) {
            countD = count;
        }
        if (amount != null && amount > 0) {
            amountD = amount;
        }

        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < countD; i++) {

            accounts.add(new Account(amountD));
        }
        logger.info("Saving list of accounts of size " + accounts.size() + " by " + Thread.currentThread().getName());

        repository.saveAll(accounts);

    }

    public void saveAccounts(List<Account> accounts) {
        repository.saveAll(accounts);
    }


    public List<Account> findAllAccounts() throws InterruptedException, ExecutionException {

        if (repository.countAccounts() >= 1) {

            CompletableFuture<List<Account>> completableFuture = CompletableFuture.completedFuture(repository.findAll());

            logger.info("Got a list of accounts from the database by " + Thread.currentThread().getName());

            return completableFuture.get();

        } else {

            logger.error("There are no accounts in the database");
            return null;
        }


    }


    public Integer findCountAccounts() {
        return repository.countAccounts();

    }

    public Integer findSumAmount() {
        return repository.sumAmount();
    }
}
