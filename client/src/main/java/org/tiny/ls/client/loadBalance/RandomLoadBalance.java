package org.tiny.ls.client.loadBalance;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/27
 */
@Slf4j
public class RandomLoadBalance implements LsRpcLoadBalance {


    @Override
    public Channel select(String serviceName, List<Channel> channelServerList) {
        if (CollectionUtils.isEmpty(channelServerList)) {
            log.warn("no lsRpc server available");
            return null;
        }

        int index = (int) (Math.random() * channelServerList.size());

        return channelServerList.get(index);
    }
}
