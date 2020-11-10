package com.gui.model;

import com.netty.channel.NettyChannelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFrame extends JFrame{

    private final JTextArea textArea;
    private final JTextArea clientList;
    private final JButton clearButton;

    public ServerFrame(String name) throws AWTException {
        super(name);
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/main/resources/icon.png");
        this.setIconImage(image);

        TrayIcon trayIcon = new TrayIcon(image, "Client");
        trayIcon.setImageAutoSize(true);

        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);

        // Top Panel
        JPanel topPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        topPanel.setLayout(flowLayout);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new clearListener());

        topPanel.add(clearButton);
        this.add(topPanel, "North");

        // Middle Panel
        clientList = new JTextArea("none", 11, 30);
        clientList.setEditable(false);
        JScrollPane midScrollPane = new JScrollPane(clientList);
        this.add(midScrollPane, "Center");

        // Bottom Panel
        textArea = new JTextArea("none", 15, 30);
        textArea.setEditable(false);
        JScrollPane botScrollPane = new JScrollPane(textArea);
        this.add(botScrollPane, "South");

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

    public void updateClientText() {
        String clients = NettyChannelManager.getInstance().getClientList();
        writeClient(clients);
    }

    private void writeClient(String clientName) {
        clientList.setText(clientName);
    }

    class clearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == clearButton) {
                textArea.setText("");
            }
        }
    }
}
