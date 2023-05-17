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
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PasswordChangeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(SkipFilter.getFlag(exchange)){
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String path = request.getPath().value();

        // 判断是否为密码修改请求，这里假设路径为 /user/password，请求方法为 POST
        if (method == HttpMethod.POST && path.equals("/user/password")) {

            // 直接通过请求， 在Controller中进行判断
            return SkipFilter.SkipFilter(chain, exchange);
        }

        // 非密码修改请求，继续执行后续的拦截器链
        return chain.filter(exchange);
    }

    // 从请求体中提取用户名
    private String extractUsernameFromBody(ServerHttpRequest request) {
        // TODO: 从请求体中提取用户名的逻辑
        // 返回请求体中的用户名，如果提取不到则返回 null
        return null;
    }

    // 从请求体中提取用户类型
    private String extractUserTypeFromBody(ServerHttpRequest request) {
        // TODO: 从请求体中提取用户类型的逻辑
        // 返回请求体中的用户类型，如果提取不到则返回 null
        return null;
    }


    @Override
    public int getOrder() {
        return FilterOrdered.PasswordChangeFilter;
    }
}
