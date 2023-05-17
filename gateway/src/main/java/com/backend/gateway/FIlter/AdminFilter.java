package com.backend.gateway.FIlter;

import com.backend.gateway.FIlter.lib.SkipFilter;
import com.backend.pojo.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AdminFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        boolean flag = SkipFilter.getFlag(exchange);
//        log.info(String.valueOf(flag));
        if(SkipFilter.getFlag(exchange)){
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String path = request.getPath().value();

        // 进行权限验证的逻辑
        if ((method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.DELETE)) {
            String type = request.getHeaders().getFirst("userType");
            // 获取用户权限级别并进行判断
            Integer userType = Integer.valueOf(type);
            if (User.isAdmin(userType)) {
                // 权限不足，返回未授权的状态码
                log.error("Invalid visit: no access");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        if (method == HttpMethod.GET && ("/user".equals(path) || "/user/".equals(path))) {
            // 获取用户权限级别并进行判断
            Integer userType = Integer.valueOf(request.getHeaders().getFirst("userType"));
            if (User.isAdmin(userType)) {
                // 权限不足，返回未授权的状态码
                log.error("invalid token");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        // 权限验证通过，继续执行过滤链
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrdered.AuthAdmin; // 定义过滤器的执行顺序，可以根据需要进行调整
    }
}