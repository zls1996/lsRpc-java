package org.tiny.ls.client.retry;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By 朱立松 on 2020/12/28
 */
@Slf4j
public class FailOverRetryStrategy implements LsRpcRetry {

    private Map<String, Integer> retryMap = new HashMap<>();

    @Override
    public boolean reachedLastRetry(String requestName, int retryTimes) {
        Integer times = retryMap.getOrDefault(requestName, 0);
        boolean result = times >= retryTimes;

        if (result) {
            retryMap.remove(requestName);
        }

        return result;
    }

    @Override
    public void multiFailCount(String requestName) {
        if (retryMap.containsKey(requestName)) {
            retryMap.put(requestName, retryMap.get(requestName)+1);
            return;
        }
        retryMap.putIfAbsent(requestName, 1);
    }
}
