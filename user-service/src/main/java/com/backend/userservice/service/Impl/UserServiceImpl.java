package com.backend.userservice.service.Impl;

import com.backend.pojo.exception.ServerException;
import com.backend.pojo.exception.UserException;
import com.backend.pojo.pojo.User;
import com.backend.userservice.mapper.UserMapper;
import com.backend.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean CreateSuperAdmin() {
        User temp = userMapper.selectByUsername("root");
        if(temp != null){
            return false;
        }
        User root = new User();
        root.setType(User.superAdmin);
        root.setUsername("root");
        root.setPassword("123456");
        userMapper.insert(root);
        return true;
    }

    @Override
    public User login(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userMapper.selectByUser(user);
    }

    @Override
    public User register(String username, String password) throws UserException, ServerException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setType(User.user);
        if(password.length() < 6){
            return null;
        }
        try{
            userMapper.insert(user);
        }catch (DuplicateKeyException e){
            throw new UserException("注册失败，用户名已经存在！");
        }catch (Exception e){
            throw new ServerException();
        }
        return user;
    }

    @Override
    public boolean UpdatePassword(String username, String password) {
        if(password.length() < 6)return false;
        User user = userMapper.selectByUsername(username);
        if(user == null){
            return false;
        }
        user.setPassword(password);
        userMapper.updateById(user);
        return true;
    }

    @Override
    public boolean UpdateUserType(String username, Integer type) {
        User user = userMapper.selectByUsername(username);
        if(user == null)return false;
        if(user.getType() == User.superAdmin)return false;
        user.setType(type);
        userMapper.updateUserType(user);
        return true;
    }

    @Override
    public boolean DeleteUser(Integer id) {
        return userMapper.deleteById(id) == 1;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public User selectById(Integer id) {
        User user = userMapper.selectById(id);
        user.setPassword(null);
        return user;
    }
}
