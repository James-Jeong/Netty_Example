package com.gui.model;

import javax.swing.*;

public class ServerFrame extends JFrame{

    JTextArea textArea;

    public ServerFrame(String name) {
        super(name);
        setBounds(500, 500, 500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
