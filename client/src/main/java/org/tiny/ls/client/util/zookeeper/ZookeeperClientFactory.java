package org.tiny.ls.client.util.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By 朱立松 on 2020/12/23
 */
public class ZookeeperClientFactory {

    private static Map<String, CuratorFramework> clientMap = new ConcurrentHashMap<>();

    // 默认一秒过期时间
    private static final int defaultTimeout = 15000;


    public static Optional<CuratorFramework> getZkClient(String addr) {
        if (null == clientMap.get(addr)) {
            initClient(addr);
        }
        return Optional.of(clientMap.get(addr));
    }



    private static void initClient(String addr) {
        initClient(addr, defaultTimeout);
    }


    private static synchronized void initClient(String addr, int sessionTimeout) {
        if (clientMap.get(addr) != null) {
            return;
        }
        // 初始时间为1s，重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        CuratorFramework client = CuratorFrameworkFactory.builder().
                connectString(addr).sessionTimeoutMs(sessionTimeout).retryPolicy(retryPolicy).build();
        client.start();
        clientMap.putIfAbsent(addr, client);
    }

    public static void closeClient(String addr) {
        CuratorFramework client = clientMap.get(addr);
        if (client == null) {
            return;
        }
        client.close();
        clientMap.remove(addr);
    }

    public static void closeAllClient() {
        clientMap.forEach((addr, client) -> {
            closeClient(addr);
        });
    }
}
