
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


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
                switch (e.getKeyChar()) {
                    case 'w':
                        controller.wKey = 'w';
                        break;
                    case 'a':
                        controller.adKey = 'a';
                        break;
                    case 'd':
                        controller.adKey = 'd';
                        break;
                    default:
                        break;
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
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d_buffered = image.createGraphics();
        paintChannels(g2d_buffered);
        for(Thing thing: controller.things) {
            thing.repaint(g2d_buffered);
        }
        BufferedImage perspectiveChessBoard = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                //to do：区域判断，只有600*600的区域进行变换
                //to do 2: 式子推导
                int rgb = image.getRGB(x, y);
                double f = 800.0 / (800 + y);
                int xv = (int) (x * f);
                int yv = (int) (y * f);
                if (xv >= 0 && xv < perspectiveChessBoard.getWidth() && yv >= 0 && yv < perspectiveChessBoard.getHeight()) {
                    perspectiveChessBoard.setRGB(xv, yv, rgb);
                }
            }
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(perspectiveChessBoard , null, null);
        controller.player.repaint(g2d);
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
