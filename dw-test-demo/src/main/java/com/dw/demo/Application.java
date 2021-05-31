package com.dw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/11 16:14
 * @version v1.0
 */
@MapperScan(value = "com.dw.demo.mapper")
@SpringBootApplication
public class Application {
    public static void main(String[] age) {
        SpringApplication.run(Application.class, age);
    }
}