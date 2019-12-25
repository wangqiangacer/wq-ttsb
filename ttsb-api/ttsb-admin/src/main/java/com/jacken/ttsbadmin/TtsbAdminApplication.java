package com.jacken.ttsbadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})  //忽略加载mysql数据库
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(value = {"com.jacken.ttsbadmin","com.jacken.wqttsbcommon"})
public class TtsbAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbAdminApplication.class, args);
    }

}
