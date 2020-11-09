package com.netty;

import com.gui.FrameManager;
import com.netty.channel.NettyChannelManager;

public class NettyMain {
    public static void main(String[] args) {
        FrameManager.getInstance().addFrame("main");
        FrameManager.getInstance().openFrame("main");

        NettyChannelManager.getInstance().startClient(8081);
    }
}
