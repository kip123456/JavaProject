package com.ui.panels;

import java.awt.*;
import javax.swing.*;

import com.Controller;
import com.data.Global;
import com.ui.UIMode;

public class MissionSelectPanel extends JPanel {

    private int buttonHeight = 50;
    private int buttonWidth = 200;
    private int buttongGap = 20;

    private class InnerPanel extends JPanel {
        InnerPanel(Dimension size, int missionNum) {
            setPreferredSize(size);
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));   



            JButton button0 = new JButton("infinite mode");
            button0.setFont(new Font("Arial", Font.BOLD, 32));
            
            button0.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            button0.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
            button0.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            button0.setAlignmentX(Component.CENTER_ALIGNMENT);
            button0.addActionListener(e -> {
                controller.init();
                controller.setGamemode(0);
                controller.setUIMode(UIMode.GAMEING);
            });
            add(button0);
            add(Box.createVerticalStrut(buttongGap));

            for(int i = 1;i<=missionNum;i++) {
                JButton button = new JButton("mission " + i);
                button.setFont(new Font("Arial", Font.BOLD, 32));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = i;
                button.addActionListener(e -> {
                    controller.init();
                    controller.setGamemode(finalI);
                    controller.setUIMode(UIMode.GAMEING);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }
            
        }
    }
    Controller controller;
    InnerPanel innerPanel;

    // missionNum 不包括无尽模式
    public MissionSelectPanel(Controller controller, int missionNum) {
        super();
        setPreferredSize(new Dimension(Global.WINDOW_WIDTH, Global.WINDOW_WIDTH));
        this.controller = controller;
        setBackground(Color.WHITE);
        setLayout(null);
        setBackground(Color.WHITE);

        JButton homeButton = new JButton();
        homeButton.setText("HOME");
        homeButton.setBounds(0, 0, 100, 30);
        homeButton.addActionListener((e) -> {
            controller.setUIMode(UIMode.HOME);
        });
        homeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(homeButton);

        JButton rougeButton = new JButton();
        rougeButton.setText("Skill Point");
        rougeButton.setBounds(110, 0, 100, 30);
        rougeButton.addActionListener((e) -> {
            controller.setUIMode(UIMode.ROUGE);
        });
        rougeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(rougeButton);
        
        innerPanel = this.new InnerPanel(new Dimension(Global.WINDOW_WIDTH/5*3, (missionNum + 1) * (buttonHeight + buttongGap)), missionNum);
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setBounds(Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5, Global.WINDOW_WIDTH/5*3, Global.WINDOW_WIDTH/5*3);
        add(scrollPane);

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
