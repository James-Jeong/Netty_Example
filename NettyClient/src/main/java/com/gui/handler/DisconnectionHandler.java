package com.gui.handler;

import com.netty.channel.NettyChannelManager;

public class DisconnectionHandler implements Runnable{
    @Override
    public void run() {
        NettyChannelManager.getInstance().stopClient();
        Thread.currentThread().interrupt();
    }
}
