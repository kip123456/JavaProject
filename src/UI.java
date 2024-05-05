
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UI {
    JFrame frame;
    GamePanel gamePanel;
    HomePanel homePanel;
    ThingManualPanel thingManualPanel;
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
            case MANUAL:
                frame.add(thingManualPanel);
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
                DataManager.bgm[1].stop();
                frame.remove(gamePanel);
                break;
            case MANUAL:
                frame.remove(thingManualPanel);
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

    void loadThings2ManualPanel() {
        int num = DataManager.monster_num;
        for (int i = 0; i < num; i++) {
            String[] name_and = new String[3];
            name_and[0] = String.valueOf(i*3);
            name_and[1] = String.valueOf(i*3+1);
            name_and[2] = String.valueOf(i*3+2);
            ThingManualPanel.ThingManualInfo info = new ThingManualPanel.ThingManualInfo
                (DataManager.monster_img[i], name_and, 0, 0, 0, 0, 0);
            thingManualPanel.insAManual(info);
        }
    }

    UI(Controller controller) {
        this.controller = controller;
        frame = new JFrame("JAVA Project");
        gamePanel = new GamePanel(controller);
        homePanel = new HomePanel(controller);
        thingManualPanel = new ThingManualPanel(800,800);
        loadThings2ManualPanel();


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
                if(e.getKeyChar() == 'x') {
                    controller.ui_lock.lock();
                    if(controller.uiMode == UIMode.MANUAL) {
                        controller.uiMode = controller.lastMode;
                        controller.lastMode = UIMode.MANUAL;
                    } else {
                        controller.lastMode = controller.uiMode;
                        controller.uiMode = UIMode.MANUAL;
                    }
                    controller.ui_lock.unlock();
                } 
            }
        });
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
