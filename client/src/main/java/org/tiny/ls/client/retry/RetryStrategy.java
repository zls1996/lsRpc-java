package org.tiny.ls.client.retry;

import lombok.Getter;

/**
 * Created By 朱立松 on 2020/12/27
 */
@Getter
public enum RetryStrategy {
    FAIL_FAST("failFast"),
    FAIL_OVER("failOver"),
    BROADCAST("broadcast");

    private String type;

    RetryStrategy(String type) {
        this.type = type;
    }}
