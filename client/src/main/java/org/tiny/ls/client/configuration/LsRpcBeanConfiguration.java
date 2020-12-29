package org.tiny.ls.client.configuration;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.tiny.ls.client.client.LsNettyRpcClient;
import org.tiny.ls.client.client.LsRpcClient;
import org.tiny.ls.client.client.handler.LsRpcHandler;
import org.tiny.ls.client.manager.LsRpcServerManager;
import org.tiny.ls.client.manager.LsRpcServerManagerImpl;
import org.tiny.ls.client.proxy.RpcServiceProxy;
import org.tiny.ls.client.registry.LsRpcConsumerRegistry;
import org.tiny.ls.client.registry.LsRpcServiceDiscovery;
import org.tiny.ls.client.registry.impl.LsRpcConsumerRegistryImplZookeeper;
import org.tiny.ls.client.registry.impl.LsRpcServiceDiscoveryImpl;
import org.tiny.ls.client.util.RpcConsumerProxyUtil;
import org.tiny.ls.client.util.SpringApplicationUtil;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created By 朱立松 on 2020/12/23
 */
@Configuration
@AutoConfigureOrder(-1)
public class LsRpcBeanConfiguration implements BeanPostProcessor {

    private static final Integer CPU_CORE_NUM = Runtime.getRuntime().availableProcessors();

    @Bean
    public ThreadPoolExecutor newThreadPoolExecutor() {
        return new ThreadPoolExecutor(CPU_CORE_NUM, CPU_CORE_NUM,
                1, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    @Bean
    @Order(1)
    public LsRpcConfiguration newLsRpcConfiguration() {
        return new LsRpcConfiguration();
    }

    @Bean
    public LsRpcClient newLsRpcClient() {
        return new LsNettyRpcClient();
    }

    @Bean
    public LsRpcHandler newLsRpcHandler() {
        return new LsRpcHandler();
    }

    @Bean
    public LsRpcServerManager newLsRpcServerManager() {
        return new LsRpcServerManagerImpl();
    }

    @Bean
    public RpcServiceProxy newRpcServiceProxy() {
        return new RpcServiceProxy();
    }

    @Bean
    public LsRpcConsumerRegistry newLsRpcConsumerRegistry() {
        return new LsRpcConsumerRegistryImplZookeeper();
    }

    @Bean
    public LsRpcServiceDiscovery newLsRpcServiceDiscovery() {
        return new LsRpcServiceDiscoveryImpl();
    }

    @Bean
    public RpcConsumerProxyUtil newRpcConsumerProxyUtil() {
        return new RpcConsumerProxyUtil();
    }

    @Bean
    public SpringApplicationUtil newSpringApplicationUtil() {
        return new SpringApplicationUtil();
    }

}
