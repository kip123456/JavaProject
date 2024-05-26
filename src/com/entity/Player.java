package com.entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.xml.crypto.Data;

import com.Controller;
import com.SEController;
import com.SaveData;
import com.data.DataManager;
import com.data.Global;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.locks.*;;

public class Player {
    public int health;
    public int defense;
    public int attack;
    private Lock healthLock=new ReentrantLock();
    private Lock defenseLock=new ReentrantLock();
    private Lock attackLock=new ReentrantLock();

    public int getHealth() {
        healthLock.lock();
        try {
            return health;
        } finally {
            healthLock.unlock();
        }
    }

    public void setHealth(int health) {
        healthLock.lock();
        try {
            this.health = health;
        } finally {
            healthLock.unlock();
        }
    }

    public int getDefense() {
        defenseLock.lock();
        try {
            return defense;
        } finally {
            defenseLock.unlock();
        }
    }

    public void setDefense(int defense) {
        defenseLock.lock();
        try {
            this.defense = defense;
        }
        finally {
            defenseLock.unlock();
        }
    }

    public int getAttack() {
        attackLock.lock();
        try {
            return attack;
        }
        finally {
            attackLock.unlock();
        }
    }

    public void setAttack(int attack) {
        attackLock.lock();
        try {
            this.attack = attack;
        }
        finally {
            attackLock.unlock();
        }
    }

    public int  magicDefense,money,exp;
    private Lock expLock=new ReentrantLock();
    public int getExp() {
        expLock.lock();
        try {
            return exp;
        } finally {
            expLock.unlock();
        }
    }
    public void setExp(int exp) {
        expLock.lock();
        try {
            this.exp = exp;
        }
        finally {
            expLock.unlock();
        }
    }

    public int posx, posy, posz;
    public int lrSteps;
    public int udSteps;
    public double rate[] = new double[7];
    public int inhurt;
    public int skill_loader;//技能条，10000为一次技能
    public int skill_damage_rate;//技能伤害比率，初始为50倍攻击
    public int skill_load_speed;//技能恢复速度
    public int skill_store;//能存储多少技能蓄力
    public boolean skill_hel_damage = false;
    public BufferedImage[] imgs;
    public Player() {
        health = 300;
        defense = 10;
        attack = 10;
        magicDefense = 10;
        lrSteps = udSteps = 0;
        posx = 0;
        posy = 150;
        posz = 0;
        skill_store = 10000;
        imgs = DataManager.player_imgs;
        skill_damage_rate = 50;
        skill_loader = 0;
        skill_load_speed = Global.skill_load_speed;
    }

    public void loadData(SaveData s) {
        money = s.coin;
        for(int i=0;i<=3;++i)
        {
            if(s.sp[i])
            health+=50*Math.pow(2, i);
        }
        for(int i=7;i<=10;++i)
        {
            if(s.sp[i])
            attack+=(i-6)*2;
        }
        for(int i=14;i<=17;++i)
        {
            if(s.sp[i])
            defense+=(i-13)*2;
        }
        for(int i=21;i<=24;++i)
        {
            if(s.sp[i])
            magicDefense+=(i-20)*10;
        }
        for(int i=4;i<=6;++i)
        if(s.sp[i])
        rate[0]+=0.1;
        for(int i=1;i<=3;++i)
        {
            for(int j=0;j<=2;++j)
            {
                if(s.sp[i*7+j+4])
                rate[i]+=0.05;
            }
        }
        if(s.sp[28])
        skill_damage_rate+=10;
        if(s.sp[29])
        skill_hel_damage = true;
        if(s.sp[30])
        skill_load_speed=60;
        if(s.sp[31])
        skill_store*=2;
        if(s.sp[32])
        rate[4]=0.1;
        if(s.sp[33])
        rate[5]=0.1;
    }
    public Player(SaveData s) {
        this();
        loadData(s);
    }
    static Random rd = new Random(System.currentTimeMillis());
    public void react(T2PMessage msg) {
        if(msg.hel < 0)
        {
            inhurt = 3;
        }
        if(rate[0]>0)
        msg.hel*=(1+rate[0]);
        if(rate[1]>0&&rd.nextInt(0, 10)*1.0/10<rate[1])
        msg.atk*=2;
        if(rate[2]>0&&rd.nextInt(0, 10)*1.0/10<rate[2])
        msg.def*=2;
        if(rate[3]>0&&rd.nextInt(0, 10)*1.0/10<rate[3])
        msg.mdef*=2;
        if(rate[4]>0&&rd.nextInt(0, 10)*1.0/10<rate[4])
        msg.money*=2;
        if(rate[5]>0&&rd.nextInt(0, 10)*1.0/10<rate[5])
        msg.exp*=2;
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
