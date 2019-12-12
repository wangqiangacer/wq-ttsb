package com.jacken.ttsbh5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtsbH5Application {

    public static void main(String[] args) {
        SpringApplication.run(TtsbH5Application.class, args);
    }

}
