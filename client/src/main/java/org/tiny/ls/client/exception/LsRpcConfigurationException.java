package org.tiny.ls.client.exception;

/**
 * Created By 朱立松 on 2020/12/21
 */
public class LsRpcConfigurationException extends RuntimeException {
    private String message;
    public LsRpcConfigurationException(String message) {
        super(message);
        this.message = message;
    }
}
