package com.gui.model;

import com.gui.handler.ConnectionHandler;
import com.netty.channel.NettyChannelManager;
import com.netty.channel.handler.NettyClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFrame extends JFrame{
    private static final Logger logger = LoggerFactory.getLogger(ClientFrame.class);

    private SystemTray systemTray;
    private final JTextArea textArea;
    private final JTextField textField;
    private final JButton sendButton;
    private final JButton startButton;
    private final JButton stopButton;

    public ClientFrame(String name) throws AWTException {
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

        // topPanel
        JPanel topPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        topPanel.setLayout(flowLayout);
        textField = new JTextField(30);

        sendButton = new JButton("전송");
        sendButton.addActionListener(new InputListener());

        startButton = new JButton("Connect");
        startButton.addActionListener(new startListener());

        stopButton = new JButton("Disconnect");
        stopButton.addActionListener(new stopListener());

        textField.setText("");
        topPanel.add(textField);
        topPanel.add(sendButton);
        topPanel.add(startButton);
        topPanel.add(stopButton);
        //topPanel.setPreferredSize(new Dimension(500, 100));
        this.add(topPanel, "Center");

        // downPanel
        textArea = new JTextArea("none", 20, 30);
        textArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.createVerticalScrollBar();
        this.add(jScrollPane, "South");
    }

    private void inputTextField(String content) {
        textField.setText(content);
    }

    private String readTextField() {
        return textField.getText();
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

    class InputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == sendButton) {
                String inputStr = String.valueOf(readTextField());
                if(inputStr == null) {
                    return;
                }

                logger.debug("Input : {}", inputStr);
                NettyClientHandler.getInstance().sendMessage(inputStr);
            }
        }
    }

    class startListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == startButton) {
                ConnectionHandler connectionHandler = new ConnectionHandler();
                connectionHandler.setPort(8081);
                Thread thread = new Thread(connectionHandler);
                thread.start();
            }
        }
    }

    class stopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == stopButton) {
                NettyChannelManager.getInstance().stopClient();
            }
        }
    }
}
