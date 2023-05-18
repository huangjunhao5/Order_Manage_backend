package com.backend.userservice.controller;


import com.backend.pojo.client.Result;
import com.backend.pojo.client.Token;
import com.backend.pojo.exception.ServerException;
import com.backend.pojo.exception.UserException;
import com.backend.pojo.pojo.User;
import com.backend.pojo.service.TokenService;
import com.backend.userservice.service.UserService;
//import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable Integer id) throws ServerException {
        User user = userService.selectById(id);
        if(user == null){
            return Result.err("查询失败", null);
        }
        return Result.ok(user);
    }

    @PostMapping("/login")
    public Result login(String username, String password) throws ServerException {
//        System.out.println(username + " " + password);
        User user = userService.login(username, password);
        if(user == null){
            return Result.err("登录失败，用户名或密码错误！", null);
        }
        String token = tokenService.getToken(user.getUsername(), user.getType());
        return Result.ok("登录成功", new Token(token, user.getType()));
    }

    @PostMapping
    public Result register(String username, String password) throws ServerException, UserException {
        User user = userService.register(username, password);
        if(user == null){
            return Result.err("注册失败");
        }
        String token = tokenService.getToken(user.getUsername(), user.getType());
        return Result.ok("注册成功", new Token(token, user.getType()));
    }

    @PostMapping("/admin")
    public Result CreateSuperAdmin() throws ServerException {
        boolean flag = userService.CreateSuperAdmin();
        if(flag){
            return Result.ok("root用户创建成功！", null);
        }else{
            return Result.err("root用户创建失败！请重置数据库后重试", null);
        }
    }
    @PutMapping
    public Result ChangePassword(
            String username,
            String password,
            @RequestHeader Integer userType,
            @RequestHeader("username")String nowUser) throws ServerException, UserException {
        log.info("nowUser: " + nowUser);
        log.info("username: " + username);
        log.info("password: " + password);
        if(!Objects.equals(username, nowUser) && !Objects.equals(userType, User.superAdmin)){

            return Result.Unauthorized();
        }
        boolean flag = userService.UpdatePassword(username, password);
        String msg = ("修改密码成功！");
        if(flag){
            return Result.ok(msg, null);
        }else{
            return Result.err("修改密码失败！请检查用户名和密码是否符号规范！", null);
        }
    }

    @PutMapping("/admin")
    public Result ChangeUserType(String username, Integer type) throws ServerException, UserException {
        if(type == User.superAdmin || username == "root"){
            return Result.err("非法操作！禁止更改root用户的权限，并且禁止将其他用户设置为superAdmin！", null);
        }
        boolean flag = userService.UpdateUserType(username, type);
        if(flag){
            return Result.ok("用户类型修改成功！", null);
        }else{
            return Result.err("用户名不存在！", null);
        }
    }

    @DeleteMapping("/{id}")
    public Result DeleteUser(@PathVariable Integer id) throws ServerException {
        boolean flag = userService.DeleteUser(id);
        if(flag){
            return Result.ok("删除成功！", null);
        }else{
            return Result.err("删除失败！", null);
        }
    }
    @GetMapping
    public Result GetAllUser() throws ServerException {
        return Result.ok(userService.getAllUser()) ;
    }
}
