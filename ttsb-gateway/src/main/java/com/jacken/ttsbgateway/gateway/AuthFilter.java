package com.jacken.ttsbgateway.gateway;

import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

/**
 * 使用gateway 实现filter功能 对token 进行校验 是否携带token
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取token
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if(token==null||token.isEmpty()){
            //响应给客户端没有权限
            ServerHttpResponse response = exchange.getResponse();
            HashMap<Object, Object> respData = Maps.newHashMap();
            respData.put("code",401);
            respData.put("message","非法请求");
            respData.put("cause","Token is empty");

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(respData);
                DataBuffer wrap = response.bufferFactory().wrap(bytes);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-type","application/json;chartset=UTF-8");
               return response.writeWith(Mono.just(wrap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return chain.filter(exchange);
    }

    /**
     * 执行顺序，数字越小，优先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
