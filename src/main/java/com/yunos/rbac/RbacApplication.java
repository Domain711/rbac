package com.yunos.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yunos.rbac.controller"})
@ComponentScan(basePackages = {"com.yunos.rbac.service"})
@MapperScan(basePackages = {"com.yunos.rbac.dao"})
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }

}
