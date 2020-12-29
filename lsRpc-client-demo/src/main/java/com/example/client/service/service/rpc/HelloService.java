package com.example.client.service.service.rpc;

import com.example.client.service.pb.Hello;
import org.tiny.ls.client.annotation.RpcConsumer;
import org.tiny.ls.client.annotation.RpcMethod;

/**
 * Created By 朱立松 on 2020/12/13
 */
@RpcConsumer(name = "org.test.helloService")
public interface HelloService {
    @RpcMethod(name = "sayHello")
    Hello.MultiReply sayHello(Hello.MultiRequest request);
}
