package com.backend.storehouseservice;

import com.backend.pojo.exception.SysExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.backend.pojo.client"})
public class StorehouseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorehouseServiceApplication.class, args);
    }

    @Bean
    public SysExceptionHandler sysExceptionHandler(){
        return new SysExceptionHandler();
    }

}
