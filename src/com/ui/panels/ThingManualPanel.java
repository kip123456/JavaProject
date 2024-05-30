package com.ui.panels;
import javax.swing.*;

import com.data.DataManager;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.*;


public class ThingManualPanel extends JPanel {
    public static class ThingManualInfo {
        public Image img;
        public String[] name_and;
        public int health, attack, defence;
        public int coins, experience;
        public int damage;
        public ThingManualInfo() {}
        public ThingManualInfo(Image img, String[] name_and, int health, int attack, int defence, int coins, int experience, int damage) {
            this.img = img;
            this.name_and = name_and;
            this.health = health;
            this.attack = attack;
            this.defence = defence;
            this.coins = coins;
            this.experience = experience;
            this.damage = damage;
        }
    }
    private static class OneThing extends JPanel {
        public OneThing(Dimension  preferredSize, ThingManualInfo info) {

            Image img = info.img;
            String[] name_and = info.name_and;
            int health = info.health;
            int attack = info.attack;
            int defence = info.defence;
            int coins = info.coins;
            int experience = info.experience;
            int damage = info.damage;
            setBackground(new Color(0,0,0,255));
            setLayout(null);
            setBackground(new Color(0,0,0,255));
            Font font = new Font("宋体", Font.PLAIN, 15);
            setPreferredSize(preferredSize);
            setBorder(BorderFactory.createDashedBorder(Color.WHITE, 3.0f, 3.0f, 3.0f, true));

            JLabel label;
            label = new JLabel();
            label.setBounds(0, 0, preferredSize.width/8, preferredSize.height);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            if(img != null) {
                label.setIcon(new ImageIcon(img));
            } else {
                label.setText("No image");
            }
            add(label);

            if(name_and.length >3 ) {
                System.out.println("name_and too long");
            }
            for(int i = 0;i<3 && i < name_and.length;i++) {
                label = new JLabel(name_and[i]);
                label.setBounds(preferredSize.width/8*1,  preferredSize.height/3*i, preferredSize.width/8, preferredSize.height/3);
                label.setFont(font);
                label.setBackground(new Color(0,0,0,255));
                label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.WHITE);
                add(label);
            }
            label = new JLabel("Health:");
            label.setBounds(preferredSize.width/8*2,  0,  preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.green);
            add(label);
            label= new JLabel(String.valueOf(health));
            label.setBounds(preferredSize.width/8*2,  preferredSize.height/3, preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            add(label);
            label = new JLabel("Attack:");
            label.setBounds(preferredSize.width/8*3, 0,  preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.red);
            add(label);
            label= new JLabel(String.valueOf(attack));
            label.setBounds(preferredSize.width/8*3,  preferredSize.height/3, preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            add(label);
            label= new JLabel("Defence:");
            label.setBounds(preferredSize.width/8*4, 0,  preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.blue);
            add(label);
            label= new JLabel(String.valueOf(defence));
            label.setBounds(preferredSize.width/8*4,  preferredSize.height/3, preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            add(label);

            label= new JLabel("Coins:");
            label.setBounds(preferredSize.width/8*5, 0,  preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.YELLOW);
            add(label);
            label= new JLabel(String.valueOf(coins));
            label.setBounds(preferredSize.width/8*5,  preferredSize.height/3, preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            add(label);

            label= new JLabel("Experiece:");
            label.setBounds(preferredSize.width/8*6, 0,  preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.GREEN);
            add(label);
            label= new JLabel(String.valueOf(experience));
            label.setBounds(preferredSize.width/8*6,  preferredSize.height/3, preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            add(label);

            label= new JLabel("Damage:");
            label.setBounds(preferredSize.width/8*7, 0,  preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.RED);
            add(label);
            label= new JLabel(String.valueOf(damage));
            label.setBounds(preferredSize.width/8*7,  preferredSize.height/3, preferredSize.width/8, preferredSize.height/3);
            label.setFont(font);
            label.setBackground(new Color(0,0,0,255));
            label.setBorder(BorderFactory.createDashedBorder(Color.WHITE, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            add(label);
        }

    }

    
    JPanel panel;
    JScrollPane scrollPane;

    private int countthings = 0;
    public void insAManual(OneThing oneThing) {
        countthings++;
        if(countthings >= 2) {
            panel.setPreferredSize(new Dimension(getPreferredSize().width/8*6, 150*countthings+100));
        }
        panel.add(oneThing);
    }

    public void insAManual(ThingManualInfo thingManualInfo) {
        insAManual(new OneThing(
            new Dimension(getPreferredSize().width-50, 150),
            thingManualInfo
        ));
    }

    public void insAManual(Image img, String[] name_and, int health, int attack, int defence, int coins, int exp, int damage) {
        insAManual(
            new ThingManualInfo(
                img,
                name_and,
                health,
                attack,
                defence,
                coins,
                exp,
                damage
            )
        );
    }

    @Override
    public void removeAll() {
        panel.removeAll();
    }

    public ThingManualPanel(int x,int y) {

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(x, y));
        panel = new JPanel();
        panel.setBackground(new Color(0,0,0,255));
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(x/8*6, y/8*6));
        scrollPane = new JScrollPane(panel);
        scrollPane.setBackground(new Color(0,0,0,255));
        scrollPane.setPreferredSize(new Dimension(x/8*6, y/8*6));
        add(scrollPane, BorderLayout.CENTER);

        JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setText("Monster Manual");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setPreferredSize(new Dimension(x/8*6, y/8*1));
        add(label, BorderLayout.NORTH);
    }

    public ThingManualPanel() {
        this(600, 600);
    }

}

