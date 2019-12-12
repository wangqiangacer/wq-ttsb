package com.jacken.ttsbapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtsbAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbAppApplication.class, args);
    }

}
