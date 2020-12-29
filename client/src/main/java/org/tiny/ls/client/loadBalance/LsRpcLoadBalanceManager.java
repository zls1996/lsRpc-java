package org.tiny.ls.client.loadBalance;


import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By 朱立松 on 2020/12/27
 */
@Slf4j
public class LsRpcLoadBalanceManager {
    private static Map<LoadBalanceStrategy, LsRpcLoadBalance> loadBalanceMap = new ConcurrentHashMap<>();

    static {
        registry(LoadBalanceStrategy.RANDOM, new RandomLoadBalance());
        registry(LoadBalanceStrategy.ROUND_ROBIN, new RoundRobinLoadBalance());
        registry(LoadBalanceStrategy.CONSISTENT_HASH, new ConsistentHashLoadBalance());
    }


    /**
     * 可根据此方法实现自定义负载均衡策略
     * @param strategy
     * @param loadBalance
     */
    public static void registry(LoadBalanceStrategy strategy, LsRpcLoadBalance loadBalance) {
        if (loadBalanceMap.containsKey(strategy)) {
            log.warn("current loadBalance already exists! not necessary to registry again, strategy:{}", strategy.getType());
            return;
        }
        loadBalanceMap.put(strategy, loadBalance);
    }

    public static LsRpcLoadBalance getLoadBalance(LoadBalanceStrategy loadBalanceStrategy) {
        return loadBalanceMap.getOrDefault(loadBalanceStrategy, null);
    }
}
