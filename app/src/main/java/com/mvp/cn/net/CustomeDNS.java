package com.mvp.cn.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;

/**
 * Created by iqiao on 2020-02-27 09:15
 * Desc: 自定义dns解析
 */
public class CustomeDNS implements Dns {

    private static class InnerClass {
        private static final CustomeDNS instance = new CustomeDNS();
    }

    public static CustomeDNS getInstance() {
        return InnerClass.instance;
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        List<InetAddress> inetAddresses = new ArrayList<>();
        List<InetAddress> hostNames = Dns.SYSTEM.lookup(hostname);
        if (!hostNames.isEmpty()) {
            inetAddresses.addAll(hostNames);
        }else{
            //增加自己服务器的ip
            inetAddresses.add(InetAddress.getByName(""));
        }
        return inetAddresses;
    }
}
