package com.example.javaweb.mapper;

import com.example.javaweb.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

@Mapper
//不用写实现类，MyBatis 会在编译期动态创建代理对象，直接 @Autowired / @Resource 就能用
public interface UserMapper {

    @Select("SELECT id, username, password FROM users " +
            "WHERE username = #{username} AND password = #{password}")
    User select(@Param("username") String username,
                @Param("password") String password);
}