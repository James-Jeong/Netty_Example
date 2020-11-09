package com.gui.handler;

import com.netty.channel.NettyChannelManager;

public class ConnectionHandler implements Runnable {

    private int port = 0;

    @Override
    public void run() {
        if (port != 0) {
            NettyChannelManager.getInstance().startClient(port);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
