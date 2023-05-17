package com.backend.pojo.client;

import com.backend.pojo.client.Result;
import com.backend.pojo.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("user-service")
public interface UserClient {
    @GetMapping("/user")
    public Result<List<User>> getAllUser();
}
