package com.zhengyu.sh.proxyselector;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Set;

/**
 * Created by zhengyu.nie on 2018/9/1.
 */
public class OkHttpProxySelector extends ProxySelector implements ApplicationListener<ApplicationEvent> {

    List<Proxy> ctripProxys;
    List<Proxy> qunarProxys;
    Set<String> ctripHosts;
    Set<String> qunarHosts;

    @Override
    public List<Proxy> select(URI uri) {

        return null;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        List<Proxy> ctripProxyList = Lists.newArrayList(
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.ctrip.com", 8080)),
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.ctrip.com", 8080)),
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.ctrip.com", 8080)));

        List<Proxy> qunarProxyList = Lists.newArrayList(
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.qunar.com", 8080)),
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.qunar.com", 8080)),
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.qunar.com", 8080)));

        Set<String> ctripHostList = Sets.newHashSet("ctrip.com", "trip.com");
        Set<String> qunarHostList = Sets.newHashSet("ctrip.com", "trip.com");

        ctripProxys = ImmutableList.copyOf(ctripProxyList);
        qunarProxys = ImmutableList.copyOf(qunarProxyList);
        ctripHosts = ImmutableSet.copyOf(ctripHostList);
        qunarHosts = ImmutableSet.copyOf(qunarHostList);

    }
}
