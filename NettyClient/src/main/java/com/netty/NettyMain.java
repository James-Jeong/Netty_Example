package com.netty;

import com.gui.FrameManager;

import java.awt.*;

public class NettyMain {
    public static void main(String[] args) throws AWTException {
        FrameManager.getInstance().addFrame("Client");
        FrameManager.getInstance().openFrame("Client");
    }
}
