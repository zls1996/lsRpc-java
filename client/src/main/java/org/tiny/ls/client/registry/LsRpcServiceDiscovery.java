package org.tiny.ls.client.registry;

import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.service.ServiceCfg;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/23
 */
public interface LsRpcServiceDiscovery {
    void discoverServer(List<ServiceCfg> services, RegistryCfg registry);
}
