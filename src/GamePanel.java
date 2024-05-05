
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.awt.event.*;


public class GamePanel extends JPanel {
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(DataManager.backgroundImg[0],lines[0].p1.x,lines[0].p1.y,lines[Global.CHANNEL_COUNT].p1.x-lines[0].p1.x, lines[0].p2.y-lines[0].p1.y,null);
        g2d.setStroke(new BasicStroke(7));
        for (int i = 0; i < Global.CHANNEL_COUNT; i++) {
        //     int mid = (lines[i].p2.y+lines[i].p1.y)/2;
        //     g2d.drawImage(DataManager.backgroundImg[0],lines[i].p1.x, lines[i].p1.y, lines[i+1].p1.x-lines[i].p1.x, mid-lines[i].p1.y,null);
        //     g2d.drawImage(DataManager.backgroundImg[0],lines[i].p1.x, mid, lines[i+1].p1.x-lines[i].p1.x, lines[i].p2.y-mid,null);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(lines[i].p1.x, lines[i].p1.y, lines[i+1].p1.x-lines[i].p1.x, lines[i].p2.y-lines[i].p1.y);
            // System.out.println(lines[i].p1.x + " " + lines[i].p1.y + " " + (lines[i+1].p1.x - lines[i].p1.x) + " " + (lines[i].p2.y-lines[i].p1.y));
        }
    }
    void road_repaint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        //设置透明度为0.5
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        final int x = 302, y = 664, width = 400, height = 33;
        g2d.drawImage(DataManager.backgroundImg[6], x, y,width,height, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d_buffered = image.createGraphics();
        paintChannels(g2d_buffered);
        road_repaint(g2d_buffered);
        for(Thing thing: controller.things) {
            thing.repaint(g2d_buffered);
        }
        controller.animationpaint(g2d_buffered,0);
        BufferedImage perspectiveChessBoard = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 200; i < image.getWidth(); i++) {

                int x = i;
                int y = j;
                if(x>=800)continue;
                // 区域判断
                if( y < 40||y>700) {
                    continue;
                }

                /**
                 * 坐标变换，目前打算是讲一个矩形映射成一个梯形
                 */
                y -= 44;
                x -= 300;
                int px = 160;
                int py = -50;

                // 用于改变上边的位置，ny越大，上边越低
                int ny = 200;

                // 映射y,不同的映射函数影响图形的长宽比例和速度
                double tmp = 1.0*y/660;

                // 二次函数，这个效果看起来长宽比例合适，但是会出现白边---有些地方画不到的情况
                // y = ny + (int)(1.0*y/660*1.0*y/660*(660-ny));

                // 线性函数
                // y = ny + (int)(1.0*y/660*(660-ny));


                y = ny + (int)(Math.pow(tmp, 1.4)*(660-ny));


                x = x  + (int)((660.0 -y)/(660.0 - py)*(px-x));
                int rgb = image.getRGB(i, j);
                // double f = 800.0 / (800 + y);
                // int xv = (int) (x * f);
                // int yv = (int) (y * f);
                int xv = x;
                int yv = y;
                xv += 300;
                yv += 44;
                if (xv >= 0 && xv < perspectiveChessBoard.getWidth() && yv >= 0 && yv < perspectiveChessBoard.getHeight()) {
                    perspectiveChessBoard.setRGB(xv, yv, rgb);
                }
            }
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(DataManager.backgroundImg[7],0,0, null);
        g2d.drawImage(DataManager.backgroundImg[4],200,100,600,144, null);
        controller.dynamic_draw(g,DataManager.backgroundImg[5],0,400,100,144,144);
        g2d.drawImage(perspectiveChessBoard , null, null);
        controller.player.repaint(g2d,controller);
        controller.player.status_repaint(g2d);
        controller.animationpaint(g,1);
        controller.bossHelBar(g);
    }

    JButton homeButton;

    public GamePanel(Controller controller) {
        super();
        setLayout(null);
        this.controller = controller;
        homeButton = new JButton();
        homeButton.setText("HOME");
        homeButton.setBounds(0, 0, 100, 30);
        homeButton.addActionListener((e) -> {
            controller.setUIMode(UIMode.HOME);
        });
        homeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(homeButton);
        setBackground(Color.WHITE);
        
        

        for(int i = 0; i <= Global.CHANNEL_COUNT; i++) {
            lines[i] = new Line(
                new Point(300+i*100, 44),
                new Point(300+i*100, Global.CHANNEL_HEIGHT*4+100)
            );
        }
    }
}