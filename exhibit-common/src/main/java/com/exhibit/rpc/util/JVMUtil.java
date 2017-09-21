package com.exhibit.rpc.util;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
/**
 * @author: ankang
 */
public class JVMUtil {
    private static final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
    private static final MemoryMXBean memory = ManagementFactory.getMemoryMXBean();

    public static String getJVMPID() {
        String pid = getJVMName().split("@")[0];
        return pid;
    }

    public static String getJVMName() {
        String name = runtime.getName();
        return name;
    }

    public static String getJVMPort() {
        String port = System.getProperty("console.port");
        String hostname = IPAddrUtil.getHostname();
        if (port == null || port.trim().length() == 0) {
            port = "880";
        }
        return hostname + ":" + port;
    }

}