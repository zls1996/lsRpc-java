package org.tiny.ls.client.loadBalance;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created By 朱立松 on 2020/12/27
 */
@Slf4j
public class RoundRobinLoadBalance implements LsRpcLoadBalance {

    Map<String, Integer> loadBalanceMap = new HashMap<>();

    @Override
    public Channel select(String serviceName, List<Channel> channelServerList) {
        if (CollectionUtils.isEmpty(channelServerList)) {
            return null;
        }
        Integer index = (loadBalanceMap.getOrDefault(serviceName, 0)) % channelServerList.size();

        loadBalanceMap.put(serviceName, index+1);
        return channelServerList.get(index);
    }
}
