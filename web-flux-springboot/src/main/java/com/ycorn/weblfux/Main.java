package com.ycorn.weblfux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @Author: wujianmin
 * @Date: 2020/4/1 10:19
 * @Function:
 * @Version 1.0
 */
@SpringBootApplication
@EnableWebFlux
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
