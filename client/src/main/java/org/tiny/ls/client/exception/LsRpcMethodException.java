package org.tiny.ls.client.exception;

/**
 * Created By 朱立松 on 2020/12/24
 */
public class LsRpcMethodException extends RuntimeException {
    private String msg;
    public LsRpcMethodException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
