
import javax.swing.*;
import java.awt.*;


public class UI {
    JFrame frame;
    MyJPanel panel;
    void repaint() {
        panel.repaint();
    }
    UI() {
        frame = new JFrame("JAVA Project");
        panel = new MyJPanel();
        frame.add(panel);
        frame.setSize(Global.WINDOW_WIDTH, Global.WINDOW_WIDTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new UI();
    }
}


class MyJPanel extends JPanel {

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
    Point transPoint(Point p) {

        return new Point(p.x *37 + Global.CHANNEL_PADDING, p.y*3 + Global.CHANNEL_PADDING);
    }
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
        paintChannels(g);
    }
    MyJPanel() {
        super();
        setBackground(Color.WHITE);
        for(int i = 0; i <= Global.CHANNEL_COUNT; i++) {
            lines[i] = new Line(
                transPoint(new Point(i*Global.CHANNEL_WIDTH, 0)),
                transPoint(new Point(i*Global.CHANNEL_WIDTH, Global.CHANNEL_HEIGHT))
            );
        }
    }
}