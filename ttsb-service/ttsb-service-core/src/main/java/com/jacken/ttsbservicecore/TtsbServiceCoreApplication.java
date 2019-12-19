package com.jacken.ttsbservicecore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jacken.ttsb.servicecore.mapper")
public class TtsbServiceCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbServiceCoreApplication.class, args);
    }

}
