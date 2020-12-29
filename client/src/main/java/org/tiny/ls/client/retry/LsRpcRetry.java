package org.tiny.ls.client.retry;

/**
 * Created By 朱立松 on 2020/12/28
 */
public interface LsRpcRetry {
    // 是否到达最后一次重试的条件
    boolean reachedLastRetry(String requestName, int retryTimes);

    // 增加失败数
    void multiFailCount(String requestName);
}
