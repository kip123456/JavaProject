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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import com.Controller;
import com.data.Global;
import com.ui.UIMode;

// call pre before switching to Rouge Panel
public class RougePanel extends JPanel {
    static private final int sp_num = 34;
    private int buttonHeight = 50;
    private int buttonWidth = 200;
    private int buttongGap = 20;

    JButton label;

    public void pre() {
        label.setText("您持有的钱数：" + Global.saveData.coin);
    }

    private class InnerPanel extends JPanel {
        InnerPanel(Dimension size) {
            setPreferredSize(size);
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));   



            for(int i = 0; i<4;i++) {
                JButton button = new JButton("初始生命+" + (i*50 + 50) + " 花费 " + (i*5 + 10));
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = i;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( finalI*5 + 10);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }
            for(int i = 4; i<7;i++) {
                JButton button = new JButton("局中生命+10% 花费" + ((i-4)*10 + 20));
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = i;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( (finalI-4)*10 + 20);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }

            for(int i = 7; i<11;i++) {
                JButton button = new JButton("初始攻击+" + ((i-7)*2 + 2) + " 花费 " + ((i-7)*10 + 10));
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = i;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( (finalI-7)*10 + 10);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }

            for(int i = 11; i<14;i++) {
                JButton button = new JButton("局中攻击+10% 花费" + ((i-11)*10 + 20));
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = i;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( (finalI-11)*10 + 20);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }

            {
                JButton button = new JButton("技能多造成10倍攻击的伤害 花费 30");
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = 28;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( 30);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }

            {
                JButton button = new JButton("技能多造成20%角色血量的伤害 花费 50");
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = 29;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( 50);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));
            }

            {
                JButton button = new JButton("技能回复速度加快20% 花费 50");
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = 30;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( 50);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));    
            }

            {
                JButton button = new JButton("技能可存储两次充能 花费 100");
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = 31;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( 100);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));   
            }

            {
                JButton button = new JButton("有10%的概率获得双倍金钱 花费 50");
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = 32;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( 50);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
                    controller.player.loadData(Global.saveData);
                });
                add(button);
                add(Box.createVerticalStrut(buttongGap));   
            }

            {
                JButton button = new JButton("有10%的概率获得双倍经验 花费 50");
                button.setFont(new Font("SimSong", Font.BOLD, 10));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                final int finalI = 33;
                button.addActionListener(e -> {
                    if(Global.saveData.sp[finalI]) return;
                    int tmp = Global.saveData.coin -( 50);
                    if(tmp < 0) {
                        JOptionPane.showMessageDialog(null,"钱不够");
                        return;
                    }
                    Global.saveData.coin = tmp;
                    Global.saveData.sp[finalI] = true;
                    pre();
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



        label = new JButton("您持有的钱数：" + Global.saveData.coin);
        label.setFont(new Font("SimSong", Font.BOLD, 18));
        label.setPreferredSize(new Dimension(300, 70));
        label.setMinimumSize(new Dimension(300, 70));
        label.setMaximumSize(new Dimension(300, 70));
        label.setBounds(300, 70, 200, 50);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label, BorderLayout.NORTH);


    }
}
