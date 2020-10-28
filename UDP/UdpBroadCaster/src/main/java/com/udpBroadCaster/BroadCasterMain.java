package com.udpBroadCaster;

import com.udpBroadCaster.broadCaster.LogEventBroadCaster;

import java.io.File;
import java.net.InetSocketAddress;

public class BroadCasterMain {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("255.255.255.255", 8888);
        LogEventBroadCaster broadCaster = new LogEventBroadCaster(socketAddress, new File("/Users/jamesj/Desktop/Netty_Example/UDP/UdpBroadCaster/src/main/java/com/udpBroadCaster/test.txt"));

        try {
            broadCaster.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             broadCaster.stop();
        }
    }
}
