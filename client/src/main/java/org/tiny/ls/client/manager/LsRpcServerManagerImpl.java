package org.tiny.ls.client.manager;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.tiny.ls.client.client.LsRpcClient;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created By 朱立松 on 2020/12/13
 */
@Component
@Slf4j
public class LsRpcServerManagerImpl implements LsRpcServerManager {

    // serviceName to Channel List
    private Map<String, CopyOnWriteArrayList<Channel>> channelsMap = new ConcurrentHashMap<>();

    // socketAddress to Channel
    private Map<SocketAddress, Channel> serverChannelMap = new ConcurrentHashMap<>();

    private AtomicInteger count = new AtomicInteger();

    @Resource
    private LsRpcClient lsRpcClient;


    @Override
    public void removeServer(InetSocketAddress address) {
        log.info("remove lsRpc server address : {}", address);
        Channel channel = serverChannelMap.get(address);
        channelsMap.forEach( (k, serviceChannels) -> {
            serviceChannels.remove(channel);
        });
        if (channel != null) {
            channel.close();
        }

        serverChannelMap.remove(address);

    }

    @Override
    public List<Channel> getServiceChannels(String serviceName) {
        CopyOnWriteArrayList<Channel> serviceChannels = channelsMap.get(serviceName);
        if (CollectionUtils.isEmpty(serviceChannels)) {
            return null;
        }
        return serviceChannels;
    }

    @Override
    public void updateServer(String serviceName, List<String> serverList) {
        log.info("start to refresh lsRpc server address list");
        if (CollectionUtils.isEmpty(serverList)) {
            log.info("no lsRpc server address available to update");
            return ;
        }

        Set<SocketAddress> socketAddressSet = new HashSet<>();
        
        for (String addr : serverList) {
            InetSocketAddress address = getAddress(addr);
            if (addr != null) {
                socketAddressSet.add(address);
            }
        }
        
        for (SocketAddress socketAddress : socketAddressSet) {
            addNewChannel(serviceName, socketAddress);
        }
    }

    private InetSocketAddress getAddress(String addr) {
        String[] arr = addr.split(":");
        if (arr.length <= 1) {
            log.error("current lsRpc server address is not valid : {}", addr);
            return null;
        }
        String host = arr[0];
        Integer port = Integer.parseInt(arr[1]);
        InetSocketAddress address = new InetSocketAddress(host, port);
        return address;
    }

    private void addNewChannel(String serviceName, SocketAddress socketAddress) {
        Channel channel = serverChannelMap.getOrDefault(socketAddress, lsRpcClient.connect(socketAddress));
        if (channel == null || !channel.isOpen()) {
            log.warn("connect to {} for service {} failed", socketAddress, serviceName);
            return;
        }
        serverChannelMap.put(socketAddress, channel);
        CopyOnWriteArrayList<Channel> serviceChannels =
                channelsMap.getOrDefault(serviceName, new CopyOnWriteArrayList<>());
        serviceChannels.add(channel);
        channelsMap.put(serviceName, serviceChannels);
    }
}
