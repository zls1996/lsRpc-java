package com.example.client.service.service.rpc;

import com.example.client.service.pb.Echo;
import org.tiny.ls.client.annotation.RpcConsumer;
import org.tiny.ls.client.annotation.RpcMethod;
import org.tiny.ls.client.loadBalance.LoadBalanceStrategy;
import org.tiny.ls.client.retry.RetryStrategy;

/**
 * Created By 朱立松 on 2020/12/14
 */
@RpcConsumer(name = "org.test.echoService")
public interface EchoService {
    @RpcMethod(name = "echo", timeout = 5000, loadBalance = LoadBalanceStrategy.ROUND_ROBIN, retryStrategy = RetryStrategy.FAIL_FAST)
    Echo.EchoRsp echo(Echo.EchoReq req);
}
