package com.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.Controller;
import com.data.Global;
import com.ui.UIMode;

public class RougePanel extends JPanel {
    static private final int sp_num = 105;
    private int buttonHeight = 50;
    private int buttonWidth = 200;
    private int buttongGap = 20;

    private class InnerPanel extends JPanel {
        InnerPanel(Dimension size) {
            setPreferredSize(size);
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));   



            for(int i = 0; i<sp_num;i++) {
                JButton button = new JButton("haha " + i);
                button.setFont(new Font("Arial", Font.BOLD, 32));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = i;
                button.addActionListener(e -> {
                    Global.saveData.sp[finalI] = true;
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }
            
        }
    }
    Controller controller;
    InnerPanel innerPanel;


    public RougePanel(Controller controller) {
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

        JButton backButton = new JButton();
        backButton.setText("back");
        backButton.setBounds(110, 0, 100, 30);
        backButton.addActionListener((e) -> {
            controller.setUIMode(controller.lastMode);
        });
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(backButton);
        
        innerPanel = this.new InnerPanel(new Dimension(Global.WINDOW_WIDTH/5*3, (sp_num + 1) * (buttonHeight + buttongGap)));
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
