package org.ls.rpc.server.configuration;

import org.ls.rpc.server.handler.PbMsgHandler;
import org.ls.rpc.server.impl.LsNettyServer;
import org.ls.rpc.server.registry.LsRpcServerRegister;
import org.ls.rpc.server.registry.impl.LsRpcServerRegisterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created By 朱立松 on 2020/12/23
 */
@Configuration
public class LsRpcBeanConfiguration {

    private static final Integer CPU_CORE_NUM = Runtime.getRuntime().availableProcessors();

    @Bean
    public ThreadPoolExecutor newThreadPoolExecutor() {
        return new ThreadPoolExecutor(CPU_CORE_NUM, CPU_CORE_NUM,
                1, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    @Bean
    public LsRpcServerConfiguration newLsRpcServerConfiguration() {
        return new LsRpcServerConfiguration();
    }

    @Bean
    public PbMsgHandler newPbMsgHandler() {
        return new PbMsgHandler();
    }

    @Bean
    public LsNettyServer newLsNettyServer() {
        return new LsNettyServer();
    }

    @Bean
    public LsRpcServerRegister newLsRpcServerRegister() {
        return new LsRpcServerRegisterImpl();
    }


 }
