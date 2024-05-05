import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.*;


public class ThingManual extends JPanel {
    public static class ThingManualInfo {
        public Image img;
        public String[] name_and;
        public int health, attack, defence;
        public int coins, experience;
        public ThingManualInfo() {}
        public ThingManualInfo(Image img, String[] name_and, int health, int attack, int defence, int coins, int experience) {
            this.img = img;
            this.name_and = name_and;
            this.health = health;
            this.attack = attack;
            this.defence = defence;
            this.coins = coins;
            this.experience = experience;
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
            setLayout(null);
            Font font = new Font("Arial", Font.PLAIN, 20);
            setPreferredSize(preferredSize);
            setBorder(BorderFactory.createDashedBorder(Color.BLACK, 3.0f, 3.0f, 3.0f, true));

            JLabel label;
            label = new JLabel();
            label.setBounds(0, 0, preferredSize.width/7, preferredSize.height);
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
                label.setBounds(preferredSize.width/7*1,  preferredSize.height/3*i, preferredSize.width/7, preferredSize.height/3);
                label.setFont(font);
                label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.BLACK);
                add(label);
            }
            label = new JLabel("Health:");
            label.setBounds(preferredSize.width/7*2,  0,  preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.green);
            add(label);
            label= new JLabel(String.valueOf(health));
            label.setBounds(preferredSize.width/7*2,  preferredSize.height/3, preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
            add(label);
            label = new JLabel("Attack:");
            label.setBounds(preferredSize.width/7*3, 0,  preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.red);
            add(label);
            label= new JLabel(String.valueOf(attack));
            label.setBounds(preferredSize.width/7*3,  preferredSize.height/3, preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.BLACK);
            add(label);
            label= new JLabel("Defence:");
            label.setBounds(preferredSize.width/7*4, 0,  preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.blue);
            add(label);
            label= new JLabel(String.valueOf(defence));
            label.setBounds(preferredSize.width/7*4,  preferredSize.height/3, preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.black);
            add(label);

            label= new JLabel("Coins:");
            label.setBounds(preferredSize.width/7*5, 0,  preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.YELLOW);
            add(label);
            label= new JLabel(String.valueOf(coins));
            label.setBounds(preferredSize.width/7*5,  preferredSize.height/3, preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.black);
            add(label);

            label= new JLabel("Experiece:");
            label.setBounds(preferredSize.width/7*6, 0,  preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.GREEN);
            add(label);
            label= new JLabel(String.valueOf(coins));
            label.setBounds(preferredSize.width/7*6,  preferredSize.height/3, preferredSize.width/7, preferredSize.height/3);
            label.setFont(font);
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 1.0f, 1.0f, 1.0f, true));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.black);
            add(label);
        }

    }

    
    JPanel panel;
    JScrollPane scrollPane;

    private int countthings = 0;
    void insAManual(OneThing oneThing) {
        countthings++;
        if(countthings >= 2) {
            panel.setPreferredSize(new Dimension(getPreferredSize().width/8*6, 150*countthings+100));
        }
        panel.add(oneThing);
    }

    void insAManual(ThingManualInfo thingManualInfo) {
        insAManual(new OneThing(
            new Dimension(getPreferredSize().width-50, 150),
            thingManualInfo
        ));
    }

    void insAManual(Image img, String[] name_and, int health, int attack, int defence, int coins, int exp) {
        insAManual(
            new ThingManualInfo(
                img,
                name_and,
                health,
                attack,
                defence,
                coins,
                exp
            )
        );
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
        JFrame frame = new JFrame("ThingManualExample");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        String[] nothing = new String[3];
        nothing[0] = "Nothing";
        nothing[1] = "Nothing";
        nothing[2] = "Nothing";
        ThingManual t = new ThingManual();
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        t.insAManual(DataManager.player_img, nothing, 100, 10, 10, 100, 100);
        frame.add(t);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

