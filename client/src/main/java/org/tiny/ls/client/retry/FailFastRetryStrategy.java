package org.tiny.ls.client.retry;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created By 朱立松 on 2020/12/28
 */
public class FailFastRetryStrategy implements LsRpcRetry {

    private Map<String, Boolean> serviceMap = new ConcurrentHashMap<>();

    @Override
    public boolean reachedLastRetry(String requestName, int retryTimes) {
        boolean result = serviceMap.containsKey(requestName);
        serviceMap.remove(requestName);
        return result;
    }

    @Override
    public void multiFailCount(String requestName) {
        serviceMap.put(requestName, true);
    }
}
