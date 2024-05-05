
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Date;



public class UI {
    JFrame frame;
    GamePanel gamePanel;
    HomePanel homePanel;
    Controller controller;
    void repaint() {
        gamePanel.repaint();
    }
    UI(Controller controller) {
        this.controller = controller;
        frame = new JFrame("JAVA Project");
        gamePanel = new GamePanel(controller);
        homePanel = new HomePanel(controller);
        // here is to show how to switch panel
        // frame.add(homePanel);

        frame.add(gamePanel);
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
                    case 'f':
                        controller.fKey = 'f';
                        break;
                    default:
                        break;
                }
                controller.wasd_lock.unlock();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        // here is to show how to switch panel
        // try{
        //     Thread.sleep(5000);
        // } catch(InterruptedException e) {
        //     e.printStackTrace();
        // }
        // frame.remove(homePanel);
        // frame.add(gamePanel);
        // frame.revalidate();
        // frame.repaint();
    }
}


class GamePanel extends JPanel {
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
        //x多出900，填满空白区域
        BufferedImage image = new BufferedImage(this.getWidth()+900, this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d_buffered = image.createGraphics();
        g2d_buffered.drawImage(DataManager.backgroundImg[3], 0,0,image.getWidth(),image.getHeight(),null);
        paintChannels(g2d_buffered);
        road_repaint(g2d_buffered);
        for(Thing thing: controller.things) {
            thing.repaint(g2d_buffered);
        }
        controller.animationpaint(g2d_buffered,0);
        BufferedImage perspectiveChessBoard = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {

                int x = i;
                int y = j;
                // 将+600~+900多出部分拼接到-300~0，实现负坐标透视
                if(x>=this.getWidth()+600)x-=image.getWidth();
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
        g2d.drawImage(DataManager.backgroundImg[4],200,100,600,144, null);
        controller.dynamic_draw(g,DataManager.backgroundImg[5],0,400,100,144,144);
        g2d.drawImage(perspectiveChessBoard , null, null);
        controller.player.repaint(g2d,controller);
        controller.player.status_repaint(g2d);
        controller.animationpaint(g,1);
    }
    GamePanel(Controller controller) {
        super();
        this.controller = controller;
        setBackground(Color.WHITE);
        for(int i = 0; i <= Global.CHANNEL_COUNT; i++) {
            lines[i] = new Line(
                new Point(300+i*100, 44),
                new Point(300+i*100, Global.CHANNEL_HEIGHT*4+100)
            );
        }
    }
}

class HomePanel extends JPanel {

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
            add(button1);
            add(Box.createVerticalStrut(button_height));
            add(button2);
            add(Box.createVerticalStrut(button_height));
            add(button3);
            
        }
    }
    Controller controller;
    InnerPanel innerPanel;
    HomePanel(Controller controller) {
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
