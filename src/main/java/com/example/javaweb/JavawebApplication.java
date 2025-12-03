package com.example.javaweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.example.javaweb.mapper")   // ← 一次性扫全部 Mapper 接口
public class JavawebApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavawebApplication.class, args);
		System.out.println("测试界面:http://localhost:8080/hello");
		System.out.println("登录界面:http://localhost:8080/login");
	}

}
