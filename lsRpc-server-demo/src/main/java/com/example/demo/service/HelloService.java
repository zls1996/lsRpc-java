package com.example.demo.service;


import com.example.demo.service.pb.Hello;

/**
 * Created By 朱立松 on 2020/12/13
 */
public interface HelloService {

    Hello.MultiReply sayHello(Hello.MultiRequest request);
}
