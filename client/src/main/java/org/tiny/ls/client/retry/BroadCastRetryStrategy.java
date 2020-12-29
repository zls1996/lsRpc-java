package org.tiny.ls.client.retry;

/**
 * Created By 朱立松 on 2020/12/28
 */
public class BroadCastRetryStrategy implements LsRpcRetry {

    @Override
    public boolean reachedLastRetry(String requestName, int retryTimes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void multiFailCount(String requestName) {
        throw new UnsupportedOperationException();
    }
}
