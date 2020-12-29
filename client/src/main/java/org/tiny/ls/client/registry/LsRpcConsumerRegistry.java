package org.tiny.ls.client.registry;

import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.service.ServiceCfg;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/13
 */
public interface LsRpcConsumerRegistry {

    void registerConsumer(List<ServiceCfg> services, String localIpv4, Integer port, RegistryCfg registryCfg);

    void unRegisterConsumer(List<ServiceCfg> services, String localIpv4, Integer port, RegistryCfg registry);
}
