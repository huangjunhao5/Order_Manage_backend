package com.backend.userservice.service;

//import org.springframework.stereotype.Service;

import com.backend.pojo.exception.ServerException;
import com.backend.pojo.exception.UserException;
import com.backend.pojo.pojo.User;

import java.util.List;

//@Service
public interface UserService {
    public boolean CreateSuperAdmin() throws  ServerException;
    public User login(String username, String password) throws  ServerException;
    public User register(String username, String password) throws UserException, ServerException;
    public boolean UpdatePassword(String username, String password) throws UserException, ServerException;
    public boolean UpdateUserType(String username, Integer type) throws UserException, ServerException;
    public boolean DeleteUser(Integer id) throws ServerException;
    public List<User> getAllUser() throws  ServerException;
    public User selectById(Integer id) throws  ServerException;
}
