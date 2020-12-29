package org.tiny.ls.client.loadBalance;

import lombok.Getter;

/**
 * Created By 朱立松 on 2020/12/27
 */
@Getter
public enum LoadBalanceStrategy {
    RANDOM("random"),
    ROUND_ROBIN("roundRobin"),
    LEAST_ACTIVE("leastActive"),
    CONSISTENT_HASH("consistentHash");


    LoadBalanceStrategy(String type) {
        this.type = type;
    }

    private String type;
}
