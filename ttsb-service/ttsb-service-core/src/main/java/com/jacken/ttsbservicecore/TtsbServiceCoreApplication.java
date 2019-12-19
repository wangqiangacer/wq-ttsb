package com.jacken.ttsbservicecore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("com.jacken.ttsb.servicecore.mapper")
@EnableFeignClients
@EnableTransactionManagement
@ComponentScan("com.jacken") //扫描common下的分页插件
public class TtsbServiceCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbServiceCoreApplication.class, args);
    }

}
