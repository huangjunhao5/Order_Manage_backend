package com.backend.userservice;

import com.backend.pojo.exception.ServerException;
import com.backend.pojo.exception.UserException;
import com.backend.pojo.pojo.User;
import com.backend.userservice.mapper.UserMapper;
import com.backend.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

//    @Test
//    void TestCreateRoot() {
//        userService.CreateSuperAdmin();
//    }

//    @Test
//    void testGetById(){
//        User user = userService.getById(1);
//        System.out.println(user);
//    }

    @Test
    void testLogin() throws ServerException {
        User root = userService.login("root", "123456");
        System.out.println(root);
    }

    @Test
    void testSelectByUsername(){
        System.out.println(userMapper.selectByUsername("root"));
    }

    @Test
    void testUpdatePassword() throws ServerException, UserException {
        userService.UpdatePassword("root", "12345678");
        System.out.println(userService.login("root", "12345678"));
    }

    @Test
    void testGetById() throws ServerException {
        User root = userService.login("root", "12345678");
        System.out.println(root.getId());
    }

    @Test
    void TestDelete() throws ServerException {
        System.out.println(userService.DeleteUser(121));
    }
}
