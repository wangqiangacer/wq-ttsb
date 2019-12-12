package com.jacken.ttsbservicecore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtsbServiceCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbServiceCoreApplication.class, args);
    }

}
