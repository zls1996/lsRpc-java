package org.ls.rpc.server.configuration;

import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.ls.rpc.server.exception.LsRpcConfigurationException;
import org.ls.rpc.server.model.registry.RegistryCfg;
import org.ls.rpc.server.model.registry.RegistryTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import org.ls.rpc.server.model.service.ServiceCfg;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/21
 */
@Configuration
@ConfigurationProperties(prefix = "ls.rpc.server")
@Data
@Component
@Slf4j
public class LsRpcServerConfiguration implements InitializingBean {

    private Integer port;

    private String env;

    private List<ServiceCfg> services;

    private RegistryCfg registry;


    @Override
    public void afterPropertiesSet() throws Exception {
        validatePort(port);
        validateEnv(env);
        validateServices(services);
        validateRegistry(registry);
    }




    private void validateRegistry(RegistryCfg registry) {
        if (StringUtil.isNullOrEmpty(registry.getType())) {
            throw new LsRpcConfigurationException(buildExceptionMsg("registryServer.type"));
        }
        if (null == RegistryTypeEnum.getRegistryType(registry.getType())) {
            throw new LsRpcConfigurationException(buildExceptionMsg(getPrefix()
                    + "registryServer.type :" + registry.getType() + " unknown"));
        }

        if (StringUtil.isNullOrEmpty(registry.getAddress())) {
            throw new LsRpcConfigurationException(buildExceptionMsg("registryServer.address"));
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
        return "ls.rpc.server.";
    }
}
