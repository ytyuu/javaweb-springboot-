package com.example.javaweb.mapper;

import com.example.javaweb.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO users(username, password, email) VALUES(#{username}, #{password}, #{email})")
    void insert(User user);

    @Update("UPDATE users SET username=#{username}, password=#{password}, email=#{email} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteById(Long id);

    @Select("SELECT id, username, password FROM users " +
            "WHERE username = #{username} AND password = #{password}")
    User select(@Param("username") String username,
                @Param("password") String password);
}