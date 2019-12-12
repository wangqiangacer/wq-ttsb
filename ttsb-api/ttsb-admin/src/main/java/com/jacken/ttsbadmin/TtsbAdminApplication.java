package com.jacken.ttsbadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TtsbAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbAdminApplication.class, args);
    }

}
