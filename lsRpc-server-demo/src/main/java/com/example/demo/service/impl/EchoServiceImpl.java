package com.example.demo.service.impl;

import com.example.demo.service.EchoService;
import com.example.demo.service.pb.Echo;
import lombok.extern.slf4j.Slf4j;
import org.ls.rpc.server.annotation.RpcMethod;
import org.ls.rpc.server.annotation.RpcProvider;
import org.ls.rpc.server.util.NetUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created By 朱立松 on 2020/12/14
 */
@RpcProvider(name = "org.test.echoService")
@Slf4j
public class EchoServiceImpl implements EchoService {

    @RpcMethod(name = "echo")
    @Override
    public Echo.EchoRsp echo(Echo.EchoReq req) {
        log.info("test rpc method echo for request : {}", req);
        Echo.EchoRsp rsp = Echo.EchoRsp.newBuilder()
                .setId(1).setDesc("ok from server :"+ NetUtils.getLocalIpv4()
                        + " with time :" + System.currentTimeMillis() / 1000L).setExtra(2).build();
        return rsp;
    }
}
