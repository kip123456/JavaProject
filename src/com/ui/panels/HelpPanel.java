package com.ui.panels;

import java.awt.*;
import javax.swing.*;

import com.Controller;
import com.data.Global;
import com.ui.UIMode;

public class HelpPanel extends JPanel {

    JTextArea textArea;
    int msgNum = 0;

    private class InnerPanel extends JPanel {

        InnerPanel(Dimension size) {
            setPreferredSize(size);
            setBackground(Color.WHITE);
            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 20));
            add(textArea);
        }
    }
    Controller controller;
    InnerPanel innerPanel;

    public void addHelpMessage(String msg) {
        ++msgNum;
        textArea.append(msgNum + "\t" + msg + "\n\n\n\n");
    }

    // missionNum 不包括无尽模式
    public HelpPanel(Controller controller) {
        super();
        setPreferredSize(new Dimension(Global.WINDOW_WIDTH, Global.WINDOW_WIDTH));
        this.controller = controller;
        setBackground(Color.WHITE);
        setLayout(null);

        JButton homeButton = new JButton();
        homeButton.setText("HOME");
        homeButton.setBounds(0, 0, 100, 30);
        homeButton.addActionListener((e) -> {
            controller.setUIMode(UIMode.HOME);
        });
        homeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(homeButton);
        
        innerPanel = this.new InnerPanel(new Dimension(Global.WINDOW_WIDTH/5*3, Global.WINDOW_WIDTH/5*3));
        innerPanel.setBounds(Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5*3, Global.WINDOW_WIDTH/5*3);
        add(innerPanel);

        JLabel nothing;
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5));
        add(nothing, BorderLayout.WEST);
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5));
        add(nothing, BorderLayout.EAST);
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5));
        add(nothing, BorderLayout.NORTH);
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5));
        add(nothing, BorderLayout.SOUTH);
    }
    
}
