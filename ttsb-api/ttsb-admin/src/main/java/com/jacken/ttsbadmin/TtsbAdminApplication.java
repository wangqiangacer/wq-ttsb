package com.jacken.ttsbadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})  //忽略加载mysql数据库
@EnableDiscoveryClient
@EnableFeignClients
public class TtsbAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbAdminApplication.class, args);
    }

}
