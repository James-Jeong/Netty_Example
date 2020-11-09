package com.gui;

import com.gui.model.ServerFrame;

import java.util.HashMap;
import java.util.Map;

public class FrameManager{
    private static FrameManager manager;
    Map<String, ServerFrame> frameMap;

    private FrameManager() {
        frameMap = new HashMap<>();
    }

    public static FrameManager getInstance() {
        if (manager == null) {
            manager = new FrameManager();
        }
        return manager;
    }

    public void addFrame(String name) {
        if(name == null) return;
        ServerFrame serverFrame = new ServerFrame(name);
        frameMap.put(name, serverFrame);
    }

    public void openFrame(String name) {
        if(name == null) return;
        ServerFrame frame = getFrame(name);
        if(frame != null) {
            frame.setVisible(true);
        }
    }

    public void closeFrame(String name) {
        if(name == null) return;
        ServerFrame frame = getFrame(name);
        if(frame != null) {
            frame.setVisible(false);
        }
    }

    public void deleteFrame(String name) {
        if(name == null) return;
        frameMap.remove(name);
    }

    public ServerFrame getFrame(String name) {
        if(name == null) return null;
        ServerFrame frame = null;
        if(frameMap.containsKey(name)) {
            frame = frameMap.get(name);
        }
        return frame;
    }
}
