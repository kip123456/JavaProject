package com.ui.panels;
import java.awt.*;
import javax.swing.*;

import com.Controller;
import com.data.Global;
import com.ui.UIMode;

public class HomePanel extends JPanel {

    private class InnerPanel extends JPanel {
        JButton button1;
        JButton button2;
        JButton button3;
        InnerPanel(Dimension size) {
            setPreferredSize(size);
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            int width = size.width;
            int height = size.height;
            int button_num = 3;
            int button_width = width / 5 * 3;
            int button_height = height / (button_num*2+1);
            button1 = new JButton("New Game");
            button2 = new JButton("Load Game");
            button3 = new JButton("Exit");

            button1.setFont(new Font("Arial", Font.BOLD, 32));
            button2.setFont(new Font("Arial", Font.BOLD, 32));
            button3.setFont(new Font("Arial", Font.BOLD, 32));

            button1.setPreferredSize(new Dimension(button_width, button_height));
            button2.setPreferredSize(new Dimension(button_width, button_height));
            button3.setPreferredSize(new Dimension(button_width, button_height));

            button1.setMinimumSize(new Dimension(button_width, button_height));
            button2.setMinimumSize(new Dimension(button_width, button_height));
            button3.setMinimumSize(new Dimension(button_width, button_height));

            button1.setMaximumSize(new Dimension(button_width, button_height));
            button2.setMaximumSize(new Dimension(button_width, button_height));
            button3.setMaximumSize(new Dimension(button_width, button_height));


            button1.setAlignmentX(Component.CENTER_ALIGNMENT);
            button2.setAlignmentX(Component.CENTER_ALIGNMENT);
            button3.setAlignmentX(Component.CENTER_ALIGNMENT);


            button1.addActionListener((e) -> {
                controller.setUIMode(UIMode.GAMEING);
            });



            add(button1);
            add(Box.createVerticalStrut(button_height));
            add(button2);
            add(Box.createVerticalStrut(button_height));
            add(button3);
            
        }
    }
    Controller controller;
    InnerPanel innerPanel;
    public HomePanel(Controller controller) {
        super();
        setPreferredSize(new Dimension(Global.WINDOW_WIDTH, Global.WINDOW_WIDTH));
        this.controller = controller;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        innerPanel = this.new InnerPanel(new Dimension(Global.WINDOW_WIDTH/5*3, Global.WINDOW_WIDTH/5*3));
        add(innerPanel, BorderLayout.CENTER);

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
