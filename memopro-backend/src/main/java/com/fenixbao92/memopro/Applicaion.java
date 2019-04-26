package com.fenixbao92.memopro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fenixbao92.memopro.dao")
public class Applicaion {

    public static void main(String[] args){
        SpringApplication.run(Applicaion.class, args);
        System.out.println("启动成功");
    }

}
