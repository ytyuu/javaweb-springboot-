package com.example.javaweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 注册处理生成的HTML文件的资源处理器
        String currentDir = System.getProperty("user.dir");
        registry.addResourceHandler("/三國共現網絡.html")
                .addResourceLocations("file:" + currentDir + File.separator);
    }
}