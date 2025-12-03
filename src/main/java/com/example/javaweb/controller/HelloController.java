package com.example.javaweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//@RestController表示这个类是一个 Spring MVC 的控制器，并且所有方法的返回值都会 直接作为 HTTP 响应体返回，而不是去跳转到某个页面
@RestController
public class HelloController {
    //GET 请求的映射注解
    @GetMapping("/hello")
    //处理请求的方法
    public String hello() {
        return "Hello Spring Boot ";
    }
}
