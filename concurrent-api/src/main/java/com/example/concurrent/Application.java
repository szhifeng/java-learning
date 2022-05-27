package com.example.concurrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动容器，扫描当前包及其以下的类
 */
@SpringBootApplication(scanBasePackages = "com.example.concurrent")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
