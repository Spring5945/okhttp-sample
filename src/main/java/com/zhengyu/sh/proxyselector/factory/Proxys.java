package com.zhengyu.sh.proxyselector.factory;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * proxy create factory
 *
 * @author zhengyu.nie
 */
public final class Proxys {
    private Proxys() {
    }

    public static Proxy newHttpProxy(String url, int port) {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url, port));
    }

    public static Proxy newSocksProxy(String url, int port) {
        return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(url, port));
    }
}
