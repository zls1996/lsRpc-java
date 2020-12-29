package org.tiny.ls.client.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Created By 朱立松 on 2020/12/22
 */
@Slf4j
public class NetUtils {
    public static String getLocalIpv4() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get localhost error : {}", e);
        }
        return null;
    }

    public static Optional<InetSocketAddress> getNetAddress(String serverAddr) {
        String[] strs = serverAddr.split(":");
        if (strs == null || strs.length <= 1) {
            log.error("parse addr to netAddress failed , address: {}", serverAddr);
            return Optional.empty();
        }
        return Optional.of(new InetSocketAddress(strs[0], Integer.parseInt(strs[1])));
    }
}
