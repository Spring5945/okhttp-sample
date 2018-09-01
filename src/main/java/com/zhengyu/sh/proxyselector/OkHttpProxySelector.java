package com.zhengyu.sh.proxyselector;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.tools.javac.util.StringUtils;
import com.zhengyu.sh.proxyselector.factory.Proxys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * Created by zhengyu.nie on 2018/9/1.
 */
@Component("okHttpProxySelector")
public class OkHttpProxySelector extends ProxySelector implements ApplicationListener<ApplicationEvent> {

    private static final Logger LOGGER = LogManager.getLogger();
    List<Proxy> ctripProxys;
    List<Proxy> qunarProxys;
    List<Proxy> httpsProxys;
    ProxySelector defaultProxySelector = ProxySelector.getDefault();
    List<Proxy> noProxy = ImmutableList.of(Proxy.NO_PROXY);
    Set<String> ctripHosts;
    Set<String> qunarHosts;

    @Override
    public List<Proxy> select(URI uri) {
        String host = StringUtils.toLowerCase(uri.getHost());
        if (ctripHosts.contains(host)) {
            return ctripProxys;
        }
        if (qunarHosts.contains(host)) {
            return qunarProxys;
        }
        if ("https".equals(StringUtils.toLowerCase(uri.getScheme()))) {
            return httpsProxys;
        }
        if (defaultProxySelector != null) {
            return defaultProxySelector.select(uri);
        }
        return noProxy;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        LOGGER.warn(String.format("connectFailed\n uri:%s\nsocketAddress:%S\nException Message:%s", uri.toString(), sa.toString(), ioe.getMessage()));
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        List<Proxy> ctripProxyList = Lists.newArrayList(
                Proxys.newHttpProxy("http.proxy.ctrip.com", 8080),
                Proxys.newSocksProxy("socks.proxy.ctrip.com", 1080));

        List<Proxy> qunarProxyList = Lists.newArrayList(
                Proxys.newHttpProxy("http.proxy.qunar.com", 8080),
                Proxys.newSocksProxy("socks.proxy.qunar.com", 1080));

        List<Proxy> httpsProxyList = Lists.newArrayList(
                Proxys.newHttpProxy("http.httpsproxy.qunar.com", 8080),
                Proxys.newSocksProxy("socks.httpsproxy.qunar.com", 1080));

        Set<String> ctripHostList = Sets.newHashSet("ctrip.com", "trip.com");
        Set<String> qunarHostList = Sets.newHashSet("qunar.com", "qunar_international.com");

        ctripProxys = ImmutableList.copyOf(ctripProxyList);
        qunarProxys = ImmutableList.copyOf(qunarProxyList);
        httpsProxys = ImmutableList.copyOf(httpsProxyList);
        ctripHosts = ImmutableSet.copyOf(ctripHostList);
        qunarHosts = ImmutableSet.copyOf(qunarHostList);

    }
}
