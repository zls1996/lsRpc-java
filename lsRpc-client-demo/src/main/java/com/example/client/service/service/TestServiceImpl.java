package com.example.client.service.service;

import com.example.client.service.pb.Echo;
import com.example.client.service.service.rpc.EchoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created By 朱立松 on 2020/12/13
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {


    @Resource
    private EchoService echoService;


    @Override
    @Scheduled(cron = "0/2 * * * * ? ")
    public void test() {
        log.info("start to execute rpc method echo");
        Echo.EchoReq echoReq =
                Echo.EchoReq.newBuilder().setId(1).setStr("echo helloworld").setOpt(4).build();
        Echo.EchoRsp echoRsp = echoService.echo(echoReq);

        log.info("echo rsp is : {}", echoRsp);
    }
}
