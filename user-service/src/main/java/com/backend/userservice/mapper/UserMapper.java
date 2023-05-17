package com.backend.userservice.mapper;

import com.backend.pojo.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    @Select("SELECT * from user where username = #{username} and password = #{password};")
    public User selectByUser(User user);
    @Select("SELECT * from user where username = #{username};")
    public User selectByUsername(String username);
//    @Delete("DELETE from user where username = #{username};")
//    public Integer deleteByUsername(String username);
    @Select("select id,username, type from user;")
    public List<User> selectAll();
    @Update("update user set type = #{type} where username = #{username};")
    public Integer updateUserType(User user);
}
