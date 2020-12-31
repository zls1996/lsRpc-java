package org.tiny.ls.client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.tiny.ls.client.client.handler.LsRpcHandler;
import org.tiny.ls.client.codec.LsRpcDecoder;
import org.tiny.ls.client.codec.LsRpcEncoder;
import org.tiny.ls.client.configuration.LsRpcConfiguration;
import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.service.ServiceCfg;
import org.tiny.ls.client.loadBalance.LoadBalanceStrategy;
import org.tiny.ls.client.model.RpcReq;
import org.tiny.ls.client.model.RpcRsp;
import org.tiny.ls.client.registry.LsRpcConsumerRegistry;
import org.tiny.ls.client.registry.LsRpcServiceDiscovery;
import org.tiny.ls.client.util.NetUtils;
import org.tiny.ls.client.util.zookeeper.ZookeeperClientFactory;

import javax.annotation.Resource;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created By 朱立松 on 2020/12/13
 */
@Component
@Slf4j
@DependsOn("lsRpcConfiguration")
public class LsNettyRpcClient implements LsRpcClient, DisposableBean {


    private EventLoopGroup group = new NioEventLoopGroup(1);


    private Bootstrap bootstrap = new Bootstrap();

    @Resource
    private LsRpcHandler rpcHandler;

    @Resource
    private LsRpcConsumerRegistry consumerRegistry;

    @Resource
    private LsRpcServiceDiscovery rpcServerDiscovery;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    private LsRpcConfiguration lsRpcConfiguration;


    @Override
    public void start(Integer port, List<ServiceCfg> services, RegistryCfg registry) {
        log.info("start to init netty lsRpc client");
        initClient(port, services, registry);
    }

    @Override
    public void start(LsRpcConfiguration config) {
        log.info("start to init netty lsRpc client");
        this.lsRpcConfiguration = config;
        initClient(config.getPort(), config.getServices(), config.getRegistry());
    }

    private void initClient(Integer port, List<ServiceCfg> services, RegistryCfg registry) {
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 0, 30));
                        pipeline.addLast(new LsRpcEncoder());
                        pipeline.addLast(new LsRpcDecoder());
                        pipeline.addLast(rpcHandler);
                    }
                }).bind(port);
        log.info("start rpc client success");
        threadPoolExecutor.execute( () -> {
            consumerRegistry.registerConsumer(services,
                    NetUtils.getLocalIpv4(), port, registry);
        });
        threadPoolExecutor.execute(() -> {
            rpcServerDiscovery.discoverServer(services,registry);
        });
    }

    @Override
    public void destroy() throws Exception {
        log.info("lsRpc client is closing, shutting down resources");
        // 注销服务在中间件上的注册
        consumerRegistry.unRegisterConsumer(this.lsRpcConfiguration.getServices(),
                NetUtils.getLocalIpv4(), this.lsRpcConfiguration.getPort(), this.lsRpcConfiguration.getRegistry());
        // 关闭Zookeeper连接
        ZookeeperClientFactory.closeAllClient();
        group.shutdownGracefully();

        log.info("lsRpc client shutdown success");
    }

    @Override
    public SynchronousQueue<RpcRsp> send(RpcReq rpcReq, LoadBalanceStrategy loadBalanceStrategy) {
        return rpcHandler.sendReq(rpcReq, loadBalanceStrategy);
    }


    @Override
    public Channel connect(SocketAddress socketAddress) {
        final ChannelFuture future = bootstrap.connect(socketAddress);
        try {
            return future.sync().channel();
        } catch (InterruptedException e) {
            log.error("connect to address {} failed : {}", socketAddress, e);
        }
        return null;
    }


    @Override
    public void shutdown() {

    }



}
