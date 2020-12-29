package org.tiny.ls.client.exception;

/**
 * Created By 朱立松 on 2020/12/24
 */
public class LsRpcConsumerException extends RuntimeException {
    private String msg;
    public LsRpcConsumerException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
