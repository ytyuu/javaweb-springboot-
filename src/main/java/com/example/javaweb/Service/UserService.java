package com.example.javaweb.Service;

import com.example.javaweb.entity.User;
import com.example.javaweb.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //@Resource优先按名字（userMapper）把 MyBatis 动态代理对象注入
    //@Autowired优先按类型
    @Resource
    private UserMapper userMapper;

    public boolean login(String username, String password){
        User user = userMapper.select(username, password);
        return user != null;
    }
}