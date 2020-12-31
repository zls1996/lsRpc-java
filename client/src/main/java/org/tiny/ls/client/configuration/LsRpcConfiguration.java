package org.tiny.ls.client.configuration;

import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.tiny.ls.client.client.LsRpcClient;
import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.registry.RegistryTypeEnum;
import org.tiny.ls.client.configuration.service.ServiceCfg;
import org.tiny.ls.client.exception.LsRpcConfigurationException;
import org.tiny.ls.client.util.RpcConsumerProxyUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created By 朱立松 on 2020/12/21
 */
@Configuration
@ConfigurationProperties(prefix = "ls.rpc.client")
@Data
@Component(value = "lsRpcConfiguration")
@Slf4j
public class LsRpcConfiguration implements InitializingBean {


    private Integer port;

    private String env;

    private List<ServiceCfg> services;

    private RegistryCfg registry;


    @Resource
    private RpcConsumerProxyUtil rpcConsumerProxyUtil;

    @Resource
    private LsRpcClient client;


    @Override
    public void afterPropertiesSet() throws Exception {
        validatePort(port);
        validateEnv(env);
        validateServices(services);
        validateRegistry(registry);
        rpcConsumerProxyUtil.registerProxies(services);
        client.start(this);
    }




    private void validateRegistry(RegistryCfg registry) {
        if (StringUtil.isNullOrEmpty(registry.getType())) {
            throw new LsRpcConfigurationException(buildExceptionMsg("registry.type"));
        }
        if (null == RegistryTypeEnum.getRegistryType(registry.getType())) {
            throw new LsRpcConfigurationException(buildExceptionMsg(getPrefix()
                    + "registry.type :" + registry.getType() + " unknown"));
        }

        if (StringUtil.isNullOrEmpty(registry.getAddress())) {
            throw new LsRpcConfigurationException(buildExceptionMsg("registry.address"));
        }
    }

    private void validateServices(List<ServiceCfg> services) {
        if (CollectionUtils.isEmpty(services)) {
            log.info("no lsRpc services configuration");
            return;
        }
        services.forEach(service -> {
            if (StringUtil.isNullOrEmpty(service.getName())) {
                throw new LsRpcConfigurationException(buildExceptionMsg("service.name"));
            }
            if (StringUtil.isNullOrEmpty(service.getClassPath())) {
                throw new LsRpcConfigurationException(buildExceptionMsg("service.classPath"));
            }
        });
    }

    private void validateEnv(String env) {
        if (StringUtil.isNullOrEmpty(env)) {
            throw new LsRpcConfigurationException(buildExceptionMsg("env"));
        }
    }

    private void validatePort(Integer port) {
        if (null == port || port <= 0) {
            throw new LsRpcConfigurationException(buildExceptionMsg("port"));
        }
    }


    private String buildExceptionMsg(String msg) {
        return getPrefix() + msg + " configuration not valid";
    }

    private String getPrefix() {
        return "ls.rpc.client.";
    }
}
