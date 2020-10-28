package com.udpMonitor;

import com.udpMonitor.monitor.LogEventMonitor;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public class MonitorMain {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress(8888);
        LogEventMonitor monitor = new LogEventMonitor(socketAddress);

        try {
            Channel channel = monitor.bind();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            monitor.stop();
        }
    }
}
