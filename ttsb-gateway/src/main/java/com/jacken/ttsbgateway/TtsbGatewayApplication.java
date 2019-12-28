package com.jacken.ttsbgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 使用springgateway作为springcloud服务的网关  使用redis的令牌桶机制实现对服务的限流
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TtsbGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtsbGatewayApplication.class, args);
    }

    //配置限流操作
    @Bean(value = "uriKeyResolver")
    public KeyResolver uriKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }


}
