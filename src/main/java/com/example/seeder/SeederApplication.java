package com.example.seeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SeederApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeederApplication.class, args);
    }

}

