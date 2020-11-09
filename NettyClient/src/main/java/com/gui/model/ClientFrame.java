package com.gui.model;

import com.netty.channel.handler.NettyClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFrame extends JFrame{
    private static final Logger logger = LoggerFactory.getLogger(ClientFrame.class);

    private final ActionListener actionListener = new InputListener();

    private final JTextArea textArea;
    private final JTextField textField;
    private final JButton button;

    public ClientFrame(String name) {
        super(name);
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // topPanel
        JPanel topPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        topPanel.setLayout(flowLayout);
        textField = new JTextField(30);
        button = new JButton("전송");
        button.addActionListener(new InputListener());
        textField.setText("");
        topPanel.add(textField);
        topPanel.add(button);
        //topPanel.setPreferredSize(new Dimension(500, 100));
        this.add(topPanel, "Center");

        // downPanel
        textArea = new JTextArea("none", 25, 30);
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
            if(e.getSource() == button) {
                String inputStr = String.valueOf(readTextField());
                if(inputStr == null) {
                    return;
                }

                logger.debug("Input : {}", inputStr);
                NettyClientHandler.getInstance().sendMessage(inputStr);
            }
        }
    }
}
