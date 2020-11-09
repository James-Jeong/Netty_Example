package com.gui;

import com.gui.model.ClientFrame;

import java.util.HashMap;
import java.util.Map;

public class FrameManager {
    private static FrameManager manager;
    Map<String, ClientFrame> frameMap;

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
        ClientFrame ClientFrame = new ClientFrame(name);
        frameMap.put(name, ClientFrame);
    }

    public void openFrame(String name) {
        if(name == null) return;
        ClientFrame frame = getFrame(name);
        if(frame != null) {
            frame.setVisible(true);
        }
    }

    public void closeFrame(String name) {
        if(name == null) return;
        ClientFrame frame = getFrame(name);
        if(frame != null) {
            frame.setVisible(false);
        }
    }

    public void deleteFrame(String name) {
        if(name == null) return;
        frameMap.remove(name);
    }

    public ClientFrame getFrame(String name) {
        if(name == null) return null;
        ClientFrame frame = null;
        if(frameMap.containsKey(name)) {
            frame = frameMap.get(name);
        }
        return frame;
    }
}
