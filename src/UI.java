
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UI {
    JFrame frame;
    GamePanel gamePanel;
    HomePanel homePanel;
    Controller controller;
    UIMode mode = UIMode.HOME;

    void addModePanel(UIMode mode) {
        switch (mode) {
            case HOME:
                frame.add(homePanel);
                break;
            case GAMEING:
                frame.add(gamePanel);
                break;
            default:
                break;
        }
    }

    void removeModePanel(UIMode mode) {
        switch (mode) {
            case HOME:
                frame.remove(homePanel);
                break;
            case GAMEING:
                frame.remove(gamePanel);
                break;
            default:
                break;
        }
    }

    void setMode(UIMode mode) {
        if (this.mode != mode) {
            removeModePanel(this.mode);
            addModePanel(mode);
            frame.revalidate();
            frame.repaint();
            this.mode = mode;
        }
    }

    void repaint() {
        gamePanel.repaint();
    }

    UI(Controller controller) {
        this.controller = controller;
        frame = new JFrame("JAVA Project");
        gamePanel = new GamePanel(controller);
        homePanel = new HomePanel(controller);
        frame.add(homePanel);

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
    }
}
