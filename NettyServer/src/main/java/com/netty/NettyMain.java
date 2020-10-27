package com.netty;

import com.netty.channel.NettyChannelManager;

public class NettyMain {
    public static void main(String[] args) {
        NettyChannelManager.getInstance().startServer(8081);
    }
}
