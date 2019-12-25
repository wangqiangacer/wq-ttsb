package com.jacken.ttsbgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * 使用springgateway作为springcloud服务的网关
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TtsbGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/jianshu")
                        .uri("http://www.jianshu.com/u/128b6effde53")
                ).build();
    }

}
