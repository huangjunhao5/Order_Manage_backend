package com.backend.gateway;

import com.backend.pojo.exception.SysExceptionHandler;
import com.backend.pojo.service.TokenService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public TokenService tokenService(){
        return new TokenService();
    }

    @Bean
    public SysExceptionHandler sysExceptionHandler(){
        return new SysExceptionHandler();
    }
}
