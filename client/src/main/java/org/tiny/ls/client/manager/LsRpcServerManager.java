package org.tiny.ls.client.manager;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created By 朱立松 on 2020/12/13
 * Rpc服务列表管理
 */
public interface LsRpcServerManager {
    /**
     * 删除server
     * @param address
     */
    void removeServer(InetSocketAddress address);

    List<Channel> getServiceChannels(String serviceName);

    void updateServer(String name, List<String> list);
}
