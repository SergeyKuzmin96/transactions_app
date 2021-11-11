package com.sergeykuzmin.transaktion_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TransaktionAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransaktionAppApplication.class, args);
    }

}
