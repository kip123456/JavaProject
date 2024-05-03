import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.*;


public class ThingManual extends JPanel {
    public static class OneThing extends JPanel {
        public OneThing(String[] name_and, int health, int attack, int defence) {
            setLayout(null);
            setPreferredSize(new Dimension(600, 150));
            if(name_and.length >3 ) {
                System.out.println("name_and too long");
            }
            for(int i = 0;i<3 && i < name_and.length;i++) {
                JLabel label = new JLabel(name_and[i]);
                label.setBounds(0,  50*i, 150, 50);
                add(label);
            }
            JLabel label = new JLabel("Health:");
            label.setBounds(150,  0, 150, 50);
            add(label);
            label= new JLabel(String.valueOf(health));
            label.setBounds(150,  50, 150, 50);
            add(label);
            label = new JLabel("Attack:s");
            label.setBounds(300, 0, 150, 50);
            add(label);
            label= new JLabel(String.valueOf(attack));
            label.setBounds(300,  50, 150, 50);
            add(label);
            label= new JLabel("Defence:");
            label.setBounds(450, 0, 150, 50);
            add(label);
            label= new JLabel(String.valueOf(defence));
            label.setBounds(450,  50, 150, 50);
            add(label);
        }
        private static String[] nothing;
        static {
            nothing = new String[3];
            nothing[0] = "Nothing";
            nothing[1] = "Nothing";
            nothing[2] = "Nothing";
        }
        public OneThing() {
            this(nothing, 0, 0, 0);
        }
    }
    JPanel panel;
    JScrollPane scrollPane;


    ThingManual(int x,int y) {
        setPreferredSize(new Dimension(x, y));
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(x, y));
        panel.add(new OneThing());
        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(x, y));
        add(scrollPane);
    }

    ThingManual() {
        this(600, 600);
    }

    public static void main(String[] args) {
        // 创建一个 JFrame 实例
        JFrame frame = new JFrame("JScrollPaneExample");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new ThingManual());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

