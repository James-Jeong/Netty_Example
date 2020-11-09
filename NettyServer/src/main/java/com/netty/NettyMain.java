package com.netty;

import com.gui.FrameManager;
import com.netty.channel.NettyChannelManager;

import java.awt.*;

public class NettyMain {
    public static void main(String[] args) throws AWTException {
        FrameManager.getInstance().addFrame("Server");
        FrameManager.getInstance().openFrame("Server");

        NettyChannelManager.getInstance().startServer(8081);
    }
}
