package org.ls.rpc.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.ls.rpc.server.annotation.RpcProvider;
import org.ls.rpc.server.codec.LsRpcDecoder;
import org.ls.rpc.server.codec.LsRpcEncoder;
import org.ls.rpc.server.configuration.LsRpcServerConfiguration;
import org.ls.rpc.server.handler.PbMsgHandler;
import org.ls.rpc.server.registry.LsRpcServerRegister;
import org.ls.rpc.server.util.NetUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created By 朱立松 on 2020/12/8
 */
@Component(value = "LsNettyServer")
@Slf4j
public class LsNettyServer implements ApplicationContextAware, DisposableBean {

    private static final int CPU_CORE_NUM = Runtime.getRuntime().availableProcessors();

    private static final EventLoopGroup dispatcherGroup = new NioEventLoopGroup(1);

    private static final EventLoopGroup workerGroup = new NioEventLoopGroup( CPU_CORE_NUM- 1);

    @Resource
    private LsRpcServerRegister register;

    @Resource
    private PbMsgHandler pbMsgHandler;

    @Resource
    private LsRpcServerConfiguration serverConfiguration;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    // rpc Service Map
    private Map<String, Object> rpcServiceMap = new ConcurrentHashMap<>(16);

    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<String, Object> rpcProviders = context.getBeansWithAnnotation(RpcProvider.class);
        rpcProviders.forEach((k, bean) -> {
            RpcProvider rpcProvider = bean.getClass().getAnnotation(RpcProvider.class);
            String name = rpcProvider.name();
            rpcServiceMap.put(name, bean);
            log.debug("load RpcProvider for name : {} success", name);
        });
        pbMsgHandler.setServiceMap(rpcServiceMap);
        log.info("all LsRpcProvider register success");
        startNettyServer();
    }


    @Override
    public void destroy() throws Exception {
        log.info("start to close lsRpc server, shutting down resources");
        String ipPort = NetUtils.getLocalIpv4()+ ":" + serverConfiguration.getPort();
        register.unRegister(ipPort, serverConfiguration.getServices(), serverConfiguration.getRegistry());
        dispatcherGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("lsRpc server shutdown successfully");
    }

    private void startNettyServer() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(dispatcherGroup, workerGroup).channel(NioServerSocketChannel.class).
                option(ChannelOption.SO_BACKLOG, 1024).
                childOption(ChannelOption.SO_KEEPALIVE, true).
                childOption(ChannelOption.TCP_NODELAY, true).
                childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 0, 30));
                        pipeline.addLast(new LsRpcEncoder());
                        pipeline.addLast(new LsRpcDecoder());
                        pipeline.addLast(pbMsgHandler);
                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind(serverConfiguration.getPort()).sync();
            String ipPort = NetUtils.getLocalIpv4()+ ":" + serverConfiguration.getPort();

            register.registryServer(ipPort, serverConfiguration.getServices(), serverConfiguration.getRegistry());
            log.info("lsRpc server start success");

            threadPoolExecutor.execute(() -> {
                try {
                    future.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    log.error("close channel failed : {}", e.getCause().toString());
                }
            });


        } catch (Exception e) {
            log.error("start lsRpc server failed, fail reason : {}", e.getCause());
            e.printStackTrace();
            dispatcherGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            throw e;
        }

    }

}
