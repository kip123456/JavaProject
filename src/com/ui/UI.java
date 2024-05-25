package com.ui;

import javax.swing.*;

import com.Controller;
import com.data.DataManager;
import com.data.Global;
import com.ui.panels.GamePanel;
import com.ui.panels.HomePanel;
import com.ui.panels.ThingManualPanel;
import com.ui.panels.ThingManualPanel.ThingManualInfo;
import com.ui.panels.MissionSelectPanel;
import com.ui.panels.HelpPanel;

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
    MissionSelectPanel missionSelectPanel;
    HelpPanel helpPanel;
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
                break;
            case MISSONSELECT:
                frame.add(missionSelectPanel);
                break;
            case HELP:
                frame.add(helpPanel);
                break;
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
                break;
            case MISSONSELECT:
                frame.remove(missionSelectPanel);
                break;
            case HELP:
                frame.remove(helpPanel);
                break;
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
        missionSelectPanel = new MissionSelectPanel(controller, Global.missionNum);
        helpPanel = new HelpPanel(controller);
        helpPanel.addHelpMessage("          游戏时按键");
        helpPanel.addHelpMessage("A D：左右移动");
        helpPanel.addHelpMessage("w：跳跃");
        helpPanel.addHelpMessage("x：怪物手册，再次按x返回");
        helpPanel.addHelpMessage("f：技能");
        helpPanel.addHelpMessage("g：商店");
        helpPanel.addHelpMessage("          机制说明");
        helpPanel.addHelpMessage("角色存在技能条（攻击属性旁），充满时会有音效提示");
        helpPanel.addHelpMessage("角色技能：对前方造成50（初始）倍攻击的伤害");
        helpPanel.addHelpMessage("       部分怪物技能解释");
        helpPanel.addHelpMessage("魔攻：攻击无视对方防御");
        helpPanel.addHelpMessage("坚固：防御至少为角色攻击-1");
        helpPanel.addHelpMessage("破甲：战前附带角色防御90%作为伤害\r\n" + //
                        "模仿：当角色属性更高时，属性等于角色属性\r\n" + //
                        "贯杀炮：第10回合造成攻击力1000%的伤害\r\n" + //
                        "怨念堆积：攻击+=仇恨值\r\n" + //
                        "怨念发酵：造成仇恨值的真伤，并使仇恨值翻倍，当被技能杀死后，仇恨值减半\r\n" + //
                        "残虐：当血量低于怪物血量时，伤害翻倍\r\n" + //
                        "净化：造成角色魔防两倍的伤害");


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
