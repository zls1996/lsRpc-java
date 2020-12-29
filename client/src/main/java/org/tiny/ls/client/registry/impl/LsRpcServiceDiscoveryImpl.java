package org.tiny.ls.client.registry.impl;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.stereotype.Component;
import org.tiny.ls.client.configuration.registry.RegistryCfg;
import org.tiny.ls.client.configuration.service.ServiceCfg;
import org.tiny.ls.client.manager.LsRpcServerManager;
import org.tiny.ls.client.registry.LsRpcServiceDiscovery;
import org.tiny.ls.client.util.NetUtils;
import org.tiny.ls.client.util.zookeeper.ZookeeperClientFactory;
import org.tiny.ls.client.util.zookeeper.ZookeeperUtil;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created By 朱立松 on 2020/12/23
 */
@Component
@Slf4j
public class LsRpcServiceDiscoveryImpl implements LsRpcServiceDiscovery {

    @Resource
    private LsRpcServerManager rpcManager;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void discoverServer(List<ServiceCfg> services, RegistryCfg registryCfg) {
        log.info("start to discover lsRpc servers for client");
        CuratorFramework client = ZookeeperClientFactory.getZkClient(registryCfg.getAddress()).get();

        services.forEach(serviceCfg -> {
            List<String> dataList = new ArrayList<>();
            String zkPath = buildServerZkPath(serviceCfg.getName());
            List<byte[]> childrenData = ZookeeperUtil.getChildrenData(client, zkPath);
            childrenData.forEach(data -> {
                String dataString = new String(data, CharsetUtil.UTF_8);
                dataList.add(dataString);
            });
            threadPoolExecutor.execute(() -> rpcManager.updateServer(serviceCfg.getName(), dataList));
            threadPoolExecutor.execute(() -> {
                if (ZookeeperUtil.addChildrenWatch(client, zkPath, new LsRpcServiceDiscoveryZkEvent(serviceCfg))) {
                    log.info("add lsRpc server config discovery watch success");
                } else {
                    log.error("add lsRpc server config discovery watch failed");
                }
            });

        });

        log.info("discover lsRpc servers for client success");


    }

    private String buildServerZkPath(String name) {
        return "/" + name.replace(".", "/") + "/servers";
    }

    /**
     * 服务发现watch事件
     */
    private class LsRpcServiceDiscoveryZkEvent implements PathChildrenCacheListener {
        private ServiceCfg serviceCfg;

        private LsRpcServiceDiscoveryZkEvent(ServiceCfg serviceCfg) {
            this.serviceCfg = serviceCfg;
        }

        @Override
        public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
            log.info("lsRpc client detect server address discovery config changed");
            PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
            String serverAddr = new String(pathChildrenCacheEvent.getData().getData(), CharsetUtil.UTF_8);
            ArrayList<String> addrList = new ArrayList<>();
            addrList.add(serverAddr);
            switch (type) {
                case CHILD_ADDED:
                    log.info("a new lsRpc server discovery added to Zookeeper , new server addr : {}", serverAddr);
                    rpcManager.updateServer(serviceCfg.getName(), addrList);
                    break;
                case CHILD_REMOVED:
                    log.info("a lsRpc server config discovery removed from zookeeper , server addr : {}", serverAddr);
                    InetSocketAddress socketAddress = NetUtils.getNetAddress(serverAddr).get();
                    rpcManager.removeServer(socketAddress);
                    break;
                case CHILD_UPDATED:
                    log.info("a lsRpc server config discovery updated from Zookeeper , updated server addr : {}", serverAddr);
                    rpcManager.updateServer(serviceCfg.getName(), addrList);
                    break;
                case INITIALIZED:
                    log.info("detect a new zookeeper server discovery connection");
            }
        }
    }
}
