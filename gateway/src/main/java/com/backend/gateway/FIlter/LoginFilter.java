package com.backend.gateway.FIlter;


import com.backend.gateway.FIlter.lib.SkipFilter;
import com.backend.pojo.service.TokenService;
//import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
//import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class LoginFilter implements GlobalFilter, Ordered {
    private TokenService tokenService;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    public LoginFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(SkipFilter.getFlag(exchange)){
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String path = request.getPath().value();
        log.info("HTTP " + request.getMethod() + " path=" + path);
        boolean requiresAuth = pathMatcher.match("/order**", path);
        requiresAuth |= pathMatcher.match("/storehouse**", path);
        requiresAuth |= pathMatcher.match("", path);
        requiresAuth |= !(path.startsWith("/user")
                && (request.getMethod().equals(HttpMethod.POST)
                || path.equals("/user/login")
                || path.startsWith("/user/admin")));
        if (requiresAuth) {
            try {
                String token = headers.getFirst("token");
                log.info(token);
                String username = tokenService.getUserNameByToken(token);
                log.info(username);
                Integer userType = tokenService.getUserTypeByToken(token);
//                log.error(String.valueOf(userType));
                // 生成新的token并在响应头中包含

                String newToken = tokenService.getToken(username, userType);
                ServerHttpResponse response = exchange.getResponse();

                response.getHeaders().add("Authorization", newToken);
                // 在这里进行用户鉴权的逻辑判断
                // 如果鉴权通过，将用户信息添加到请求的Attributes中，方便后续处理
                request = request.mutate()
                        .header("username", username)
                        .header("userType", String.valueOf(userType))
                        .build();
            } catch (Exception e) {
                log.error("invalid token");
                log.error(e.getMessage());
//                e.printStackTrace();
                // 鉴权失败，返回未授权的状态码
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }else{
            return SkipFilter.SkipFilter(chain, exchange);
        }
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return FilterOrdered.LoginFilter;
    }
}
