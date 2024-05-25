package com.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.data.Global;
import com.Controller;


/**
 * call pre before switching to ShopPanel
 */
public class ShopPanel extends JPanel {

    int buyedTimes = 0;
    JButton highlightButton;
    JPanel innerPanel;
    JLabel label;
    Controller controller;
    Runnable todo;

    public void pre() {
        SwingUtilities.invokeLater(() -> {
            label.setText("Exp: " + controller.player.getExp() + "  Price: " + (5 + buyedTimes));
        });
    }
    public ShopPanel(Dimension size,Controller controller) {
        this.controller = controller;
        setPreferredSize(size);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        innerPanel=new JPanel();
        innerPanel.setPreferredSize(new Dimension(size.width/3, size.height/3*2));
        innerPanel.setBackground(Color.BLACK);
        add(innerPanel, BorderLayout.CENTER);
        JLabel nothing;
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/3, Global.WINDOW_WIDTH/3));
        add(nothing, BorderLayout.WEST);
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/3, Global.WINDOW_WIDTH/3));
        add(nothing, BorderLayout.EAST);
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/6, Global.WINDOW_WIDTH/6));
        add(nothing, BorderLayout.NORTH);
        nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(Global.WINDOW_WIDTH/6, Global.WINDOW_WIDTH/6));
        add(nothing, BorderLayout.SOUTH);

        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SimSun", Font.BOLD, 32));
        label.setText("您所持有的经验为：，需要付出的经验为：");
        label.setPreferredSize(new Dimension(size.width/3, 50));
        label.setBackground(Color.BLACK);
        innerPanel.add(label);
        innerPanel.add(Box.createVerticalStrut(20));



        int buttonGap = 20;

        JButton button1;

        button1 = new JButton();
        button1.setForeground(Color.WHITE);
        button1.setText("增加 500 生命");
        button1.setFont(new Font("SimSun", Font.BOLD, 32));
        button1.setBackground(Color.BLACK);
        button1.setPreferredSize(new Dimension(size.width/3, 50));
        button1.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                if(highlightButton != null) {
                    highlightButton.setBackground(Color.BLACK);
                }
                highlightButton = button1;
                button1.setBackground(Color.GRAY);
            });
            todo = () -> controller.player.setHealth(controller.player.getHealth()+500);
        });
        innerPanel.add(button1);
        innerPanel.add(Box.createVerticalStrut(buttonGap));

        JButton button2;

        button2 = new JButton();
        button2.setForeground(Color.WHITE);
        button2.setText("增加 2 攻击");
        button2.setFont(new Font("SimSun", Font.BOLD, 32));
        button2.setBackground(Color.BLACK);
        button2.setPreferredSize(new Dimension(size.width/3, 50));
        button2.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                if(highlightButton != null) {
                    highlightButton.setBackground(Color.BLACK);
                }
                highlightButton = button2;
                button2.setBackground(Color.GRAY);
            });
            todo = () -> controller.player.setAttack(controller.player.getAttack()+2);
        });
        innerPanel.add(button2);
        innerPanel.add(Box.createVerticalStrut(buttonGap));

        JButton button3;

        button3 = new JButton();
        button3.setForeground(Color.WHITE);
        button3.setText("增加 2 防御");
        button3.setFont(new Font("SimSun", Font.BOLD, 32));
        button3.setBackground(Color.BLACK);
        button3.setPreferredSize(new Dimension(size.width/3, 50));
        button3.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                if(highlightButton != null) {
                    highlightButton.setBackground(Color.BLACK);
                }
                highlightButton = button3;
                button3.setBackground(Color.GRAY);
            });
            todo = () -> controller.player.setDefense(controller.player.getDefense()+2);
        });
        innerPanel.add(button3);
        innerPanel.add(Box.createVerticalStrut(buttonGap));

        JButton button4;

        button4 = new JButton();
        button4.setForeground(Color.WHITE);
        button4.setText("确定");
        button4.setFont(new Font("SimSun", Font.BOLD, 32));
        button4.setBackground(Color.BLACK);
        button4.setPreferredSize(new Dimension(size.width/3, 50));
        button4.addActionListener((e) -> {
            if(controller.player.getExp() < 5 + buyedTimes) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "您的经验不足，无法购买！", "提示", JOptionPane.WARNING_MESSAGE);
                });
            } else {
                controller.player.setExp(controller.player.getExp() - 5 - buyedTimes);
                todo.run();
                controller.setUIMode(controller.lastMode);
            }
        });
        innerPanel.add(button4);
        innerPanel.add(Box.createVerticalStrut(buttonGap));

        JButton button5;

        button5 = new JButton();
        button5.setForeground(Color.WHITE);
        button5.setText("返回");
        button5.setFont(new Font("SimSun", Font.BOLD, 32));
        button5.setBackground(Color.BLACK);
        button5.setPreferredSize(new Dimension(size.width/3, 50));
        button5.addActionListener((e) -> {
            controller.setUIMode(controller.lastMode);
        });
        innerPanel.add(button5);
        innerPanel.add(Box.createVerticalStrut(buttonGap));
    }

    public ShopPanel(Controller controller) {
        this(new Dimension(Global.WINDOW_WIDTH,Global.WINDOW_WIDTH), controller);
    }
}
