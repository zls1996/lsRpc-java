package org.ls.rpc.server.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
}
