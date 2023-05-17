package com.backend.gateway.FIlter;

import com.backend.gateway.FIlter.lib.SkipFilter;
import com.backend.pojo.pojo.User;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
import lombok.extern.slf4j.Slf4j;
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
public class RootFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        log.info(String.valueOf(SkipFilter.getFlag(exchange)));
        if(SkipFilter.getFlag(exchange)){
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String path = request.getPath().value();

        Integer type = Integer.valueOf(request.getHeaders().getFirst("userType"));
        // 进行权限验证的逻辑
        if(path.startsWith("/user") && method == HttpMethod.DELETE){
//            log.info("path.startsWith(\"/user\") && method == HttpMethod.DELETE");
            return RootFilter(type, exchange, chain);
        }

        if(("/user".equals(path) && method == HttpMethod.GET) || ("/user/admin/".equals(path) && method == HttpMethod.PUT)){
//            log.info("(\"/user\".equals(path) && method == HttpMethod.GET) || (\"/user/admin/\".equals(path) && method == HttpMethod.PUT)");
            return RootFilter(type,exchange,chain);
        }

        // 权限验证通过，继续执行过滤链
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return FilterOrdered.AuthSuperAdmin; // 定义过滤器的执行顺序，可以根据需要进行调整
    }

    private Mono<Void> RootFilter(Integer userType, ServerWebExchange exchange, GatewayFilterChain chain){
        if(userType == User.superAdmin){
            return chain.filter(exchange);
        }else{
            log.error("Invalid Visit: No Access");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

    }
}