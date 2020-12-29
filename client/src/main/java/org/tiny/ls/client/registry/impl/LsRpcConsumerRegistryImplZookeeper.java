package org.tiny.ls.client.registry.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Component;
import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.service.ServiceCfg;
import org.tiny.ls.client.registry.LsRpcConsumerRegistry;
import org.tiny.ls.client.util.zookeeper.ZookeeperClientFactory;
import org.tiny.ls.client.util.zookeeper.ZookeeperUtil;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/13
 */
@Component
@Slf4j
public class LsRpcConsumerRegistryImplZookeeper implements LsRpcConsumerRegistry {


    @Override
    public void registerConsumer(List<ServiceCfg> services, String ip, Integer port, RegistryCfg registryCfg) {
        log.info("start to register client {}:{} to zookeeper", ip, port);
        CuratorFramework client = ZookeeperClientFactory.getZkClient(registryCfg.getAddress()).get();
        String ipPort = (ip + ":" + port);
        byte[] bytes = ipPort.getBytes();
        services.forEach(serviceCfg -> {
            String zkPath = buildZkPath(serviceCfg.getName(), ipPort);
            if (!ZookeeperUtil.createNode(client,zkPath, bytes)) {
                log.error("register service to zk with failed, name : {}, path : {}", serviceCfg.getName(), zkPath);
            }
        });
    }

    @Override
    public void unRegisterConsumer(List<ServiceCfg> services, String ip, Integer port, RegistryCfg registryCfg) {
        log.info("start to unRegister client {}:{} to zookeeper", ip, port);
        CuratorFramework client = ZookeeperClientFactory.getZkClient(registryCfg.getAddress()).get();
        String ipPort = (ip + ":" + port);
        services.forEach(serviceCfg -> {
            String zkPath = buildZkPath(serviceCfg.getName(), ipPort);
            if (! ZookeeperUtil.deleteNode(client, zkPath)) {
                log.error("unRegister lsRpc client to zookeeper failed");
            } else {
                log.info("unRegister lsRpc client to zookeeper success");
            }
        });
    }

    private String buildZkPath(String name, String ipPort) {
        return "/" + name.replace(".", "/") + "/clients/"+ipPort;
    }
}
