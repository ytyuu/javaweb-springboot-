package com.example.javaweb.controller;

import com.example.javaweb.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@Controller默认实现跳转到页面  http://localhost:8080/login
@Controller  // 注意：不能用 @RestController

    public class LoginController {

        //GET请求展示页面
        @GetMapping("/login")
        public String LoginPage() {
            return "login";  // 对应 templates/login.html
        }

        //注入实例（构造器注入）
        private final UserService userService;

        public LoginController(UserService userService) {
            this.userService = userService;
        }

        //POST请求
        @PostMapping("/login")
        public String doLogin(@RequestParam String username,
                              @RequestParam String password,
                              RedirectAttributes ra) {
            boolean ok = userService.login(username, password);
            if (ok) return "redirect:/main";
            ra.addFlashAttribute("error", "账号或密码错误");
            //跳回原界面
            return "redirect:/login";
        }

    }


