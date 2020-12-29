package com.example.demo.service.impl;

import com.example.demo.service.HelloService;
import com.example.demo.service.pb.Hello;
import lombok.extern.slf4j.Slf4j;
import org.ls.rpc.server.annotation.RpcMethod;
import org.ls.rpc.server.annotation.RpcProvider;

/**
 * Created By 朱立松 on 2020/12/13
 */
@RpcProvider(name = "org.test.helloService")
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    @RpcMethod(name = "sayHello")
    public Hello.MultiReply sayHello(Hello.MultiRequest request) {
        log.info("test rpc method sayHello for request : {}", request);
        final Hello.MultiReply reply = Hello.MultiReply.newBuilder().setAddress("wuhan").setResult(1000).build();
        return reply;
    }
}
