package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tiny.ls.client.annotation.EnableLsRpcClient;
import org.tiny.ls.client.configuration.LsRpcBeanConfiguration;


// 一定要添加EnableLsRpcClient注解
@EnableLsRpcClient
@EnableScheduling
@SpringBootApplication
public class LsRpcClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsRpcClientDemoApplication.class, args);
    }

}
