package com.netty;

import com.gui.FrameManager;

public class NettyMain {
    public static void main(String[] args) {
        FrameManager.getInstance().addFrame("Client");
        FrameManager.getInstance().openFrame("Client");
    }
}
