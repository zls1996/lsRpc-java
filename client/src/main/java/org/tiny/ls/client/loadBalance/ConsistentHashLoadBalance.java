package org.tiny.ls.client.loadBalance;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.tiny.ls.client.util.NetUtils;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/27
 */
@Slf4j
public class ConsistentHashLoadBalance implements LsRpcLoadBalance {

    @Override
    public Channel select(String serviceName, List<Channel> channelServerList) {
        if (CollectionUtils.isEmpty(channelServerList)) {
            return null;
        }
        String ip = NetUtils.getLocalIpv4();
        int ipHashCode = Math.abs(ip.hashCode());
        int index = ipHashCode % channelServerList.size();
        return channelServerList.get(index);
    }
}
