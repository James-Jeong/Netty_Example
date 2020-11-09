package com.gui.model;

import javax.swing.*;
import java.awt.*;

public class ServerFrame extends JFrame{

    private SystemTray systemTray;
    JTextArea textArea;

    public ServerFrame(String name) throws AWTException {
        super(name);
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/main/resources/icon.png");
        this.setIconImage(image);

        TrayIcon trayIcon = new TrayIcon(image, "Client");
        trayIcon.setImageAutoSize(true);

        systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);

        textArea = new JTextArea("none", 5, 10);
        textArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(textArea);
        this.add(jScrollPane, "Center");

    }

    public void writeText(String content) {
        textArea.setText(content);
    }

    public void appendText(String content) {
        textArea.append(content);
    }

    public String readText() {
        return textArea.getText();
    }
}
