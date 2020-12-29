package org.ls.rpc.server.registry.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.ls.rpc.server.exception.LsRpcRegistryException;
import org.ls.rpc.server.model.registry.RegistryCfg;
import org.ls.rpc.server.model.service.ServiceCfg;
import org.ls.rpc.server.registry.LsRpcServerRegister;
import org.ls.rpc.server.util.zookeeper.ZookeeperClientFactory;
import org.ls.rpc.server.util.zookeeper.ZookeeperUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/11
 */
@Component
@Slf4j
public class LsRpcServerRegisterImpl implements LsRpcServerRegister {


    @Override
    public void registryServer(String ipPort, List<ServiceCfg> services, RegistryCfg registryCfg) throws Exception {
        log.info("start to register lsRpc server to zookeeper");
        CuratorFramework client = ZookeeperClientFactory.getZkClient(registryCfg.getAddress()).get();

        services.forEach(serviceCfg -> {
            String zkPath = buildServerZkPath(serviceCfg.getName(), ipPort);
            if (!ZookeeperUtil.createNode(client,zkPath, ipPort.getBytes())) {
                log.error("register lsRpc service : {} to zk with path : {} failed", serviceCfg.getName(), zkPath);
                throw new LsRpcRegistryException("register lsRpc to zookeeper failed");
            }

        });
        log.info("register lsRpc server to zookeeper success");

    }

    @Override
    public void unRegister(String ipPort, List<ServiceCfg> services, RegistryCfg registryCfg) throws Exception {
        log.info("start to unRegister lsRpc server to zookeeper");
        CuratorFramework client = ZookeeperClientFactory.getZkClient(registryCfg.getAddress()).get();

        services.forEach(serviceCfg -> {
            String zkPath = buildServerZkPath(serviceCfg.getName(), ipPort);
            if (! ZookeeperUtil.deleteNode(client, zkPath)) {
                log.error("unRegister lsRpc server to zookeeper failed");
            } else {
                log.info("unRegister lsRpc server to zookeeper success");
            }
        });

    }

    private String buildServerZkPath(String name, String ipPort) {
        String s = name.replace(".", "/");
        return "/" + s + "/servers/" + ipPort;
    }
}
