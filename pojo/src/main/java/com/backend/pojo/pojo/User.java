package com.backend.pojo.pojo;


import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer type;

    public void setPassword(String password) {
        if(password == null)return;
        this.password = DigestUtils.md5Hex(password);
    }

    public static Integer superAdmin = 0;
    public static Integer admin = 1;
    public static Integer user = 2;
    public static boolean isAdmin(Integer userType){
        return (Objects.equals(userType, User.superAdmin) || Objects.equals(userType, User.admin));
    }
    public boolean isSuperAdmin(Integer userType){
        return (Objects.equals(userType, User.superAdmin));
    }
}
