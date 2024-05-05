import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.*;


public class ThingManual extends JPanel {
    private static class OneThing extends JPanel {
        public OneThing(Dimension  preferredSize, Image img, String[] name_and, int health, int attack, int defence) {
            setLayout(null);
            Font font = new Font("Arial", Font.PLAIN, 20);
            setPreferredSize(preferredSize);
            setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f, 3.0f, true));

            JLabel label;
            label = new JLabel();
            label.setBounds(0, 0, preferredSize.width/5, preferredSize.height);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
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
                label.setBounds(preferredSize.width/5*1,  preferredSize.height/3*i, preferredSize.width/5, preferredSize.height/3);
                label.setFont(font);
                label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.BLACK);
                add(label);
            }
            label = new JLabel("Health:");
            label.setBounds(preferredSize.width/5*2,  0,  preferredSize.width/5, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.green);
            add(label);
            label= new JLabel(String.valueOf(health));
            label.setBounds(preferredSize.width/5*2,  preferredSize.height/3, preferredSize.width/5, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
            add(label);
            label = new JLabel("Attack:");
            label.setBounds(preferredSize.width/5*3, 0,  preferredSize.width/5, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.red);
            add(label);
            label= new JLabel(String.valueOf(attack));
            label.setBounds(preferredSize.width/5*3,  preferredSize.height/3, preferredSize.width/5, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
            add(label);
            label= new JLabel("Defence:");
            label.setBounds(preferredSize.width/5*4, 0,  preferredSize.width/5, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.blue);
            add(label);
            label= new JLabel(String.valueOf(defence));
            label.setBounds(preferredSize.width/5*4,  preferredSize.height/3, preferredSize.width/5, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.black);
            add(label);
        }
        private static String[] nothing;
        static {
            nothing = new String[3];
            nothing[0] = "Nothing";
            nothing[1] = "Nothing";
            nothing[2] = "Nothing";
        }

        public OneThing(Image img, String[] name_and, int health, int attack, int defence) {
            this(new Dimension(550, 150), img, name_and, health, attack, defence);
        }

        public OneThing() {
            this(DataManager.player_img, nothing, 0, 0, 0);
        }
    }
    JPanel panel;
    JScrollPane scrollPane;

    private int countthings = 0;
    void insAManual(OneThing oneThing) {
        countthings++;
        if(countthings >= 2) {
            panel.setPreferredSize(new Dimension(getWidth(), 150*countthings+100));
        }
        panel.add(oneThing);
    }

    void insAManual(Image img, String[] name_and, int health, int attack, int defence) {
        insAManual(new OneThing(img, name_and, health, attack, defence));
    }

    ThingManual(int x,int y) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(x, y));
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(x/8*6, y/8*6));
        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(x/8*6, y/8*6));
        add(scrollPane, BorderLayout.CENTER);

        JLabel label = new JLabel();
        label.setText("Monster Manual");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setPreferredSize(new Dimension(x/8*6, y/8*1));
        add(label, BorderLayout.NORTH);
    }

    ThingManual() {
        this(600, 600);
    }

    public static void main(String[] args) {
        // 创建一个 JFrame 实例
        JFrame frame = new JFrame("JScrollPaneExample");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        ThingManual t = new ThingManual();
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        t.insAManual(new OneThing());
        frame.add(t);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

