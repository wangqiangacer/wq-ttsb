package com.jacken.ttsbserviceschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtsbServiceScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbServiceScheduleApplication.class, args);
    }

}
