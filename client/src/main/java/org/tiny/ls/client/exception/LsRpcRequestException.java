package org.tiny.ls.client.exception;

/**
 * Created By 朱立松 on 2020/12/23
 */
public class LsRpcRequestException extends RuntimeException {
    private String msg;
    public LsRpcRequestException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
