package com.entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.xml.crypto.Data;

import com.Controller;
import com.Global;
import com.SEController;
import com.data.DataManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Player {
    public int health, defense, attack, magicDefense,money,exp;
    public int posx, posy, posz;
    public int lrSteps;
    public int udSteps;
    public int inhurt;
    public int skill_loader;//技能条，10000为一次技能
    public int skill_damage_rate;//技能伤害比率，初始为10倍攻击
    public int skill_load_speed;//技能恢复速度
    public int skill_store;//能存储多少技能蓄力
    public BufferedImage[] imgs;
    public Player() {
        health = 10000;
        defense = 10;
        attack = 10;
        magicDefense = 10;
        lrSteps = udSteps = 0;
        posx = 0;
        posy = 150;
        posz = 0;
        skill_store = 10000;
        imgs = DataManager.player_imgs;
        skill_damage_rate = 10;
        skill_loader = 0;
        skill_load_speed = Global.skill_load_speed*4;
        //ps：测试模式，血量和技能恢复速度增加
    }
    public void react(T2PMessage msg) {
        if(msg.hel < 0)
        {
            inhurt = 3;
        }
        health += msg.hel;
        attack += msg.atk;
        defense += msg.def;
        magicDefense += msg.mdef;
        money += msg.money;
        exp += msg.exp;
    }
    public void setMove(MovingState direc)
    {
        switch (direc) {
            case UP:
                if(udSteps == 0 && posz == 0)
                udSteps = Global.TICKS_PER_PLAYER_MOVE;
                break;
            case LEFT:
                if(lrSteps == 0)
                lrSteps = -Global.TICKS_PER_PLAYER_MOVE;
                break;
            case RIGHT:
                if(lrSteps == 0)
                lrSteps = Global.TICKS_PER_PLAYER_MOVE;
                break;
            default:
                break;
        }
    }
    void skillLoad(SEController se)
    {
        if(skill_loader < skill_store)
        {
            skill_loader += skill_load_speed;
            if(skill_loader >= skill_store)
            {
                skill_loader = skill_store;
                se.add(DataManager.se[3]);
            }
        }
    }
    public void move(SEController se) {
        if(inhurt > 0) --inhurt;
        skillLoad(se);
        if(udSteps == 0 && posz > 0) udSteps = -Global.TICKS_PER_PLAYER_MOVE;

        if(lrSteps <0) {
            posx--;
            if(posx <0) posx = 0;
            ++lrSteps;
        }
        if(lrSteps >0) {
            posx ++;
            if(posx > Global.TICKS_PER_PLAYER_MOVE*(Global.CHANNEL_COUNT-1)) posx = (Global.CHANNEL_COUNT-1)*Global.CHANNEL_WIDTH;
            --lrSteps;
        }
        if(udSteps <0) {
            posz --;
            if(posz<0) posz = 0;
            ++udSteps;
        }
        if(udSteps >0) {
            posz ++;
            if(posz > Global.TICKS_PER_PLAYER_MOVE) posz =0;
            --udSteps;
        }
    }

    //将posz计算分离至repaint，否则对空中怪物meet计算会出错
    public Rectangle transRectangle() {
        return new Rectangle((int)(1.0* posx/Global.TICKS_PER_PLAYER_MOVE*100+325),
        posy*4+44, 50,50);
    }
    //posz参数修正改为二次函数
    public void repaint(Graphics g,Controller controller)
    {
        Rectangle rect = transRectangle();
        rect.translate(0, - ((int)(1.0*posz/Global.TICKS_PER_PLAYER_MOVE*Global.MAX_PLAYER_JUMP))-posz*posz*2);
        controller.dynamic_draw(g, imgs[inhurt], 0,rect.x, rect.y, rect.width, rect.height);
    }

    public void status_repaint(Graphics g)
    {
        g.drawImage(DataManager.backgroundImg[1], 0, 100,200,600, null);
        int[] hc = new int[]{health,attack,defense,magicDefense,exp,money};
        g.setFont(g.getFont().deriveFont(30f));
        for(int i=0;i<=5;++i)
        {
            g.drawImage(DataManager.icon[i], 25, 200+75*i,50,50 ,null);
            g.setColor(Color.WHITE);
            g.drawString(hc[i]+"", 90, 235+75*i);
        }
        g.setColor(Color.black);
        g.drawRect(25, 330, 100, 10);
        g.setColor(Color.yellow);
        g.fillRect(26, 331, (skill_loader%10001)/102, 8);
        g.setFont(g.getFont().deriveFont(12f));
        g.drawString(skill_loader/10000+" / "+skill_store/10000, 130, 334);
    }
    public int useSkill()
    {
        if(skill_loader>=10000&&lrSteps<=1&&udSteps<=1)
        {
            skill_loader-=10000;
            return (posx+lrSteps)/Global.TICKS_PER_PLAYER_MOVE;
        }
        return -1;
    }
}
