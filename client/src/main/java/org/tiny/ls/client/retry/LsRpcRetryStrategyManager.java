package org.tiny.ls.client.retry;

import lombok.extern.slf4j.Slf4j;
import org.tiny.ls.client.loadBalance.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By 朱立松 on 2020/12/28
 */
@Slf4j
public class LsRpcRetryStrategyManager {
    private static Map<RetryStrategy, LsRpcRetry> retryMap = new ConcurrentHashMap<>();

    static {
        registry(RetryStrategy.FAIL_FAST, new FailFastRetryStrategy());
        registry(RetryStrategy.FAIL_OVER, new FailOverRetryStrategy());
        registry(RetryStrategy.BROADCAST, new BroadCastRetryStrategy());
    }


    /**
     * 可根据此方法实现自定义负载均衡策略
     * @param strategy
     * @param rpcRetry
     */
    public static void registry(RetryStrategy strategy, LsRpcRetry rpcRetry) {
        if (retryMap.containsKey(strategy)) {
            log.warn("current retryPolicy already exists! not necessary to registry again, retryPolicy:{}", strategy.getType());
            return;
        }
        retryMap.put(strategy, rpcRetry);
    }

    public static LsRpcRetry getRetryPolicy(RetryStrategy strategy) {
        return retryMap.getOrDefault(strategy, null);
    }
}
