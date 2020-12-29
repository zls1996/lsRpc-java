package org.tiny.ls.client.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.tiny.ls.client.configuration.service.ServiceCfg;
import org.tiny.ls.client.proxy.RpcServiceFactory;
import org.tiny.ls.client.proxy.RpcServiceProxy;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created By 朱立松 on 2020/12/21
 * 动态注入lsRpc中的Service相关配置，并生成对应的动态代理类到Spring Container中
 */
@Component
@Slf4j
public class RpcConsumerProxyUtil {

    @Resource
    private SpringApplicationUtil applicationUtil;

    @Resource
    private RpcServiceProxy rpcServiceProxy;

    /**
     * 注册代理对象到spring容器中
     * @param services
     */
    public void registerProxies(List<ServiceCfg> services) {
        if (CollectionUtils.isEmpty(services)) {
            log.error("rpc service cfg not registerProxies yet");
            return;
        }

        for (ServiceCfg serviceCfg : services) {
            Class<?> rpcClientService = null;
            try {
                rpcClientService = Class.forName(serviceCfg.getClassPath());
            } catch (ClassNotFoundException e) {
                log.error("load class : {} failed, maybe the path not exists, cause id : {}",
                        serviceCfg.getClassPath(), e);
            }

            if (rpcClientService == null) {
                continue;
            }
            ConfigurableApplicationContext application = (ConfigurableApplicationContext) applicationUtil.getApplication();
            registerBean(rpcClientService, application);
        }
    }

    /**
     * 注册rpcConsumer对象到Spring容器中
     * @param clazz
     * @param application
     */
    private void registerBean(Class<?> clazz, ConfigurableApplicationContext application) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
        RpcServiceFactory rpcServiceFactory = new RpcServiceFactory(clazz, rpcServiceProxy);
        definition.setBeanClass(rpcServiceFactory.getClass());

        // 根据类型注入
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) application.getBeanFactory();
        beanFactory.registerBeanDefinition(clazz.getName(), definition);
        log.info("registerProxies rpc Service : {} success", clazz.getName());
    }



}
