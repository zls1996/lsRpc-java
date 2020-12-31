package org.tiny.ls.client.client;

import io.netty.channel.Channel;
import org.tiny.ls.client.configuration.LsRpcConfiguration;
import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.service.ServiceCfg;
import org.tiny.ls.client.loadBalance.LoadBalanceStrategy;
import org.tiny.ls.client.model.RpcReq;
import org.tiny.ls.client.model.RpcRsp;

import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Created By 朱立松 on 2020/12/13
 */
public interface LsRpcClient {
    SynchronousQueue<RpcRsp> send(RpcReq rpcReq, LoadBalanceStrategy loadBalanceStrategy);

    Channel connect(SocketAddress socketAddress);

    void shutdown();

    void start(Integer port, List<ServiceCfg> services, RegistryCfg registry);

    void start(LsRpcConfiguration lsRpcConfiguration);
}
