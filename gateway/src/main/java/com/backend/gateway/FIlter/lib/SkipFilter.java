package com.backend.gateway.FIlter.lib;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


// 工具类，实现跳过后续的拦截器
public class SkipFilter {
    public static Mono<Void> SkipFilter(GatewayFilterChain chain, ServerWebExchange exchange){
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.OK);
////            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//        byte[] responseBytes = "Access granted".getBytes();
//        response.getHeaders().setContentLength(responseBytes.length);
//        return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBytes)));
        exchange.getAttributes().put("SKIP_FILTER", true);
        return chain.filter(exchange);
    }

    public static boolean getFlag(ServerWebExchange exchange){
        if (exchange.getAttributes().containsKey("SKIP_FILTER") && exchange.getAttribute("SKIP_FILTER").equals(true)) {
            // 请求设置了 SKIP_FILTER 属性，跳过后续拦截器，直接路由到目标微服务
            return true;
        } else {
            // 执行其他拦截器逻辑
            // ...
            return false;
        }
    }
}
