package org.learning.mvn.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动容器，扫描当前包及其以下的类
 */
@SpringBootApplication(scanBasePackages = "org.learning.mvn.test")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
