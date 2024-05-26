package com.ui;

import javax.swing.*;

import com.Controller;
import com.Saver;
import com.data.DataManager;
import com.data.Global;
import com.ui.panels.GamePanel;
import com.ui.panels.HomePanel;
import com.ui.panels.ThingManualPanel;
import com.ui.panels.ThingManualPanel.ThingManualInfo;
import com.ui.panels.MissionSelectPanel;
import com.ui.panels.RougePanel;
import com.ui.panels.ShopPanel;
import com.ui.panels.HelpPanel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
    ShopPanel shopPanel;
    Controller controller;
    UIMode mode = UIMode.HOME;
    private RougePanel rougePanel;

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
            case SHOP:
                shopPanel.pre();
                frame.add(shopPanel);
                break;
            case ROUGE:
                rougePanel.pre();
                frame.add(rougePanel);
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
            case SHOP:
                frame.remove(shopPanel);
                break;
            case ROUGE:
                frame.remove(rougePanel);
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
        try {
            SwingUtilities.invokeAndWait(() -> {
                gamePanel.repaint();
            });
        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    static Image getMonsterImage(int id) {
        BufferedImage img = DataManager.monster_img[id/4];
        int width = img.getWidth(null)/4;
        int height = img.getHeight(null)/4;
        return (Image)(img.getSubimage(0, (id%4)*height, width, height));
    }

    static String[] getMonsterDescription(int id) {
        int talents = DataManager.monster_data[id][5];
        /**
        *  采用二进制方式存储，其中：
        *  1 ： 浮空
        *  2 ： 先攻
        *  4 ： 三连击
        *  8 ： 魔攻
        *  16 ： 坚固
        *  32 ： 破甲（战前附带角色防御90%作为伤害）
        *  64 ： 模仿（当角色属性更高时，属性等于角色属性）
        *  128 ：贯杀炮（第10回合造成攻击力1000%的伤害）
        *  256 : 怨念堆积（攻击+=仇恨值）
        *  512 ：怨念发酵（造成仇恨值的真伤，并使仇恨值翻倍，当被技能杀死后，仇恨值减半）
        *  1024 : 残虐（当血量低于怪物血量时，伤害翻倍）
        *  2048 : 净化（造成角色魔防两倍的伤害）
        */

        String[] talentsname = new String[12];
        talentsname[0] = "浮空";
        talentsname[1] = "先攻";
        talentsname[2] = "三连击";
        talentsname[3] = "魔攻";
        talentsname[4] = "坚固";
        talentsname[5] = "破甲";
        talentsname[6] = "模仿";
        talentsname[7] = "贯杀炮";
        talentsname[8] = "怨念堆积";
        talentsname[9] = "怨念发酵";
        talentsname[10] = "残虐";
        talentsname[11] = "净化";
        List<String> ans = new LinkedList<>();
        for(int i=0;i<12;i++) {
            if((talents&(1<<i))!=0) {
                ans.add(talentsname[i]);
            }
        }
        return ans.toArray(new String[ans.size()]);
    }
    public void loadThings2ManualPanel(List<Thing> things, Player player) {
        thingManualPanel.removeAll();
        Set<Integer > ids = new HashSet<Integer>();
        for(Thing thing: things) {
            if(thing instanceof Monster) {
                Monster monster = (Monster) thing;
                int id = monster.getId();
                if(ids.contains(id)) continue;
                ids.add(id);
                ThingManualInfo info = new ThingManualInfo(
                    getMonsterImage(id),
                    getMonsterDescription(id),
                    monster.getHel(),
                    monster.getAtk(player),
                    monster.getDef(player),
                    monster.getMoney(player),
                    monster.getExp(player),
                    monster.getDamage(player)
                );
                thingManualPanel.insAManual(info);

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

        shopPanel = new ShopPanel(controller);
        rougePanel = new RougePanel(controller);


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
                if(e.getKeyChar() == 'g') {
                    controller.ui_lock.lock();
                    if(controller.uiMode == UIMode.SHOP) {
                        // do nothing
                    } else {
                        controller.lastMode = controller.uiMode;
                        controller.uiMode = UIMode.SHOP;
                    }
                    controller.ui_lock.unlock();
                }
            }
        });
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Global.saveData.coin = controller.player.money;
                Saver.save(Global.saveData);
                System.out.println("saving data");
                System.exit(0);
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
