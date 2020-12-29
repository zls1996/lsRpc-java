package org.tiny.ls.client.loadBalance;

import io.netty.channel.Channel;

import java.util.List;

/**
 * Created By 朱立松 on 2020/12/27
 */
public interface LsRpcLoadBalance {
    Channel select(String serviceName, List<Channel> channelServerList);
}
