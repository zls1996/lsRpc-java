package org.ls.rpc.server.registry;


import org.ls.rpc.server.model.registry.RegistryCfg;
import org.ls.rpc.server.model.service.ServiceCfg;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/8
 */
public interface LsRpcServerRegister {

    void registryServer(String ipPort, List<ServiceCfg> services, RegistryCfg registry) throws Exception;

    void unRegister(String ipPort, List<ServiceCfg> services, RegistryCfg registry) throws Exception;
}
