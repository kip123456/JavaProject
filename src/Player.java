import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.xml.crypto.Data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Player {
    int health, defense, attack, magicDefense;
    int posx, posy, posz;
    int lrSteps;
    int udSteps;
    int inhurt;
    BufferedImage[] imgs;
    public Player() {
        health = 10000;
        defense = 10;
        attack = 10;
        magicDefense = 10;
        lrSteps = udSteps = 0;
        posx = 0;
        posy = 150;
        posz = 0;
        imgs = DataManager.player_imgs;
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
    public void move() {
        if(inhurt > 0) --inhurt;
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
    Rectangle transRectangle() {
        return new Rectangle((int)(1.0* posx/Global.TICKS_PER_PLAYER_MOVE*100+325),
        posy*4+44, 50,50);
    }
    //posz参数修正改为二次函数
    void repaint(Graphics g,Controller controller)
    {
        Rectangle rect = transRectangle();
        rect.translate(0, - ((int)(1.0*posz/Global.TICKS_PER_PLAYER_MOVE*Global.MAX_PLAYER_JUMP))-posz*posz*2);
        controller.dynamic_draw(g, imgs[inhurt], 0,rect.x, rect.y, rect.width, rect.height);
    }

    void status_repaint(Graphics g)
    {
        g.drawImage(DataManager.backgroundImg[1], 0, 100,200,600, null);
        int[] hc = new int[]{health,attack,defense,magicDefense};
        g.setFont(g.getFont().deriveFont(30f));
        for(int i=0;i<=3;++i)
        {
            g.drawImage(DataManager.icon[i], 25, 200+75*i,50,50 ,null);
            g.setColor(Color.WHITE);
            g.drawString(hc[i]+"", 90, 235+75*i);
        }
    }
}
