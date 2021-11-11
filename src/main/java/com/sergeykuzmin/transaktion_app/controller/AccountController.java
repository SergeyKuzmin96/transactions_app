package com.sergeykuzmin.transaktion_app.controller;

import com.sergeykuzmin.transaktion_app.model.Account;
import com.sergeykuzmin.transaktion_app.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final static Logger logger = LogManager.getLogger(AccountController.class.getName());

    @Autowired
    private AccountService service;

    @PostMapping
    @Operation(
            summary = "Добавление счетов",
            description = "Данный запрос добавит в базу данных счета. Без инициализации количества счетов, и суммы средств, будет добавлен один счет с суммой средств 10000"
    )
    public ResponseEntity saveAccounts(@RequestParam(value = "count", required = false) @Parameter(description = "Колиечество счетов") Integer count,
                                       @RequestParam(value = "amount", required = false) @Parameter(description = "Количество средств на одном счете") Integer amount) {

        service.saveAccounts(count, amount);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(
            summary = "Получение списка счетов"
    )
    public List<Account> findAllAccounts() {

        List<Account> accounts = null;

        try {

            accounts = service.findAllAccounts();
        } catch (InterruptedException e) {

            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {

            logger.error(e.getMessage());
        }

        return accounts;
    }


    @PutMapping(value = "/transact")
    @Operation(
            summary = "Проведение транзакций между счетами",
            description = "Данный запрос проведет количество транзакций , которое требуется указать в соответствующем поле"
    )
    public List<Account> transact(@RequestParam(value = "count", required = false) @Parameter(description = "Количество транзакций") Integer countOfTransact) {

        if (service.findCountAccounts() > 1) {

            try {
                logger.info("Start of transactions ");
                logger.info("The amount of funds in the accounts before transactions = " + service.findSumAmount());

                CompletableFuture<List<Account>> future = service.transact(countOfTransact);
                List<Account> accounts = future.get();

                logger.info(countOfTransact + " transactions completed ");
                logger.info("The amount of funds in the accounts after transactions = " + service.findSumAmount());

                return accounts;

            } catch (ExecutionException e) {

                logger.error(e.getMessage());
            } catch (InterruptedException e) {

                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        } else {
            logger.error("There are not enough accounts to conduct transactions");
        }
        return null;
    }


}