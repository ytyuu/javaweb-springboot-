package com.example.javaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String main() {
        return "main";   // templates/main.html
    }
    
    @GetMapping("/")
    public String index() {
        return "main";   // 默认返回主页
    }
}