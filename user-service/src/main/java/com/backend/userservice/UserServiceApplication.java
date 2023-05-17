package com.backend.userservice;

import com.backend.pojo.exception.SysExceptionHandler;
import com.backend.pojo.service.TokenService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
//@MapperScan({"com.backend.userservice.mapper"})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
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
