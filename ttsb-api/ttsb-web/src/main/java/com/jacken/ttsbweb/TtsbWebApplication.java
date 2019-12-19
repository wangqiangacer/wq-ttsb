package com.jacken.ttsbweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtsbWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbWebApplication.class, args);
    }

}
