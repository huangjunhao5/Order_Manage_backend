package com.backend.gateway.controller;

import com.backend.pojo.client.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @RequestMapping
    public Result test(){
        log.info("Test Controller Visited.");
        return Result.ok("");
    }
}
