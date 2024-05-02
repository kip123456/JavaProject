
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class UI {
    JFrame frame;
    MyJPanel panel;
    Controller controller;
    void repaint() {
        panel.repaint();
    }
    UI(Controller controller) {
        this.controller = controller;
        frame = new JFrame("JAVA Project");
        panel = new MyJPanel(controller);
        frame.add(panel);
        frame.setSize(Global.WINDOW_WIDTH, Global.WINDOW_WIDTH);

        // 监听键盘输入wasd
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                controller.wasd_lock.lock();
                controller.wasd = ' ';
                if(controller.wasd == ' ') {
                    switch (e.getKeyChar()) {
                        case 'w':
                            controller.wasd = 'w';
                            break;
                        case 'a':
                            controller.wasd = 'a';
                            break;
                        case 's':
                            controller.wasd = 's';
                            break;
                        case 'd':
                            controller.wasd = 'd';
                            break;
                        default:
                            break;
                    }
                }
                controller.wasd_lock.unlock();
            }
        });


        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


class MyJPanel extends JPanel {
    Controller controller;

    static class Line {
        Point p1, p2;
        Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
    Line[] lines = new Line[Global.CHANNEL_COUNT+1];

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    /**
     * 将来可能会做投影变换
     * @param p
     * @return
     */
    void paintChannels(Graphics g) {
        for (int i = 0; i < Global.CHANNEL_COUNT; i++) {
            g.setColor(Color.GRAY);
            g.fillRect(lines[i].p1.x, lines[i].p1.y, lines[i+1].p1.x-lines[i].p1.x, lines[i].p2.y-lines[i].p1.y);
            System.out.println(lines[i].p1.x + " " + lines[i].p1.y + " " + (lines[i+1].p1.x - lines[i].p1.x) + " " + (lines[i].p2.y-lines[i].p1.y));
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paintChannels(g);
        // new Monster(1, 1, 50).repaint(g);
        // Controller.repaint(g);
        for(Thing thing: controller.things) {
            thing.repaint(g);
        }
        controller.player.repaint(g);
    }
    MyJPanel(Controller controller) {
        super();
        this.controller = controller;
        setBackground(Color.WHITE);
        for(int i = 0; i <= Global.CHANNEL_COUNT; i++) {
            lines[i] = new Line(
                new Point(i*Global.CHANNEL_WIDTH, 0),
                new Point(i*Global.CHANNEL_WIDTH, Global.CHANNEL_HEIGHT)
            );
        }
    }
}
