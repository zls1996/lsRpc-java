package org.tiny.ls.client.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.net.*;
import java.util.Enumeration;
import java.util.Optional;

/**
 * Created By 朱立松 on 2020/12/22
 */
@Slf4j
public class NetUtils {
    public static String getLocalIpv4() {
        if (isWindowsPlatform()) {
            return getWindowsLocalIpv4();
        }
        return getLinuxLocalIpv4();
    }

    private static String getLinuxLocalIpv4() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                String name = intf.getName();
                if (!name.contains("docker") && !name.contains("lo")) {
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            String ipaddress = inetAddress.getHostAddress().toString();
                            if (!ipaddress.contains("::") && !ipaddress.contains("0:0:") && !ipaddress.contains("fe80")) {
                                ip = ipaddress;
                                System.out.println(ipaddress);
                            }
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            log.error("get Linux ip v4 failed , cause : {}", ex.getCause().toString());
            ip = "127.0.0.1";
        }
        log.debug("get current Linux platform ip v4 is : {}", ip);
        return ip;
    }

    private static String getWindowsLocalIpv4() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("get localhost error : {}", e);
        }
        return null;
    }

    private static boolean isWindowsPlatform() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
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
