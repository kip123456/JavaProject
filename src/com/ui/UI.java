package com.ui;

import javax.swing.*;

import com.Controller;
import com.data.DataManager;
import com.data.Global;
import com.ui.panels.GamePanel;
import com.ui.panels.HomePanel;
import com.ui.panels.ThingManualPanel;
import com.ui.panels.ThingManualPanel.ThingManualInfo;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import com.entity.Monster;
import com.entity.Thing;
import com.entity.Player;

public class UI {
    JFrame frame;
    GamePanel gamePanel;
    HomePanel homePanel;
    ThingManualPanel thingManualPanel;
    Controller controller;
    UIMode mode = UIMode.HOME;

    public void addModePanel(UIMode mode) {
        switch (mode) {
            case HOME:
                frame.add(homePanel);
                break;
            case GAMEING:
                frame.add(gamePanel);
                break;
            case MANUAL:
                loadThings2ManualPanel(controller.things, controller.player);
                frame.add(thingManualPanel);
            default:
                break;
        }
    }

    public void removeModePanel(UIMode mode) {
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

    public void setMode(UIMode mode) {
        if (this.mode != mode) {
            removeModePanel(this.mode);
            addModePanel(mode);
            frame.revalidate();
            frame.repaint();
            this.mode = mode;
        }
    }

    public void repaint() {
        gamePanel.repaint();
    }

    public void loadThings2ManualPanel(List<Thing> things, Player player) {
        thingManualPanel.removeAll();
        int i = 0;
        for(Thing thing: things) {
            if(thing instanceof Monster) {
                Monster monster = (Monster) thing;
                ThingManualInfo info = new ThingManualInfo(
                    monster.myImage,
                    new String[]{
                        String.valueOf(i*3),
                        String.valueOf(i*3+1),
                        String.valueOf(i*3+2)
                    },
                    monster.getHel(),
                    monster.getAtk(player),
                    monster.getDef(player),
                    monster.getMoney(player),
                    monster.getExp(player),
                    monster.getDamage(player)
                );
                thingManualPanel.insAManual(info);

                i++;
            }
        }
    }

    public UI(Controller controller) {
        this.controller = controller;
        frame = new JFrame("JAVA Project");
        gamePanel = new GamePanel(controller);
        homePanel = new HomePanel(controller);
        thingManualPanel = new ThingManualPanel(800,800);


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
