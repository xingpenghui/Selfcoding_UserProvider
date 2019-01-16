package com.feri.selfcoding_userprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.feri.dao.user")
@ImportResource("classpath:dubboprovider.xml")
@ServletComponentScan("com.feri.selfcoding_userprovider.druid")
public class SelfcodingUserproviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfcodingUserproviderApplication.class, args);
    }

}

