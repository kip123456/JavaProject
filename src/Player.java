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
    BufferedImage myImage;
    public Player() {
        health = 100;
        defense = 10;
        attack = 10;
        magicDefense = 10;
        lrSteps = udSteps = 0;
        posx = 0;
        posy = 150;
        posz = 0;
        myImage = DataManager.player_img;
    }
    public void react(T2PMessage msg) {
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

    Rectangle transRectangle() {//to do : change the moving speed
        return new Rectangle((int)(1.0* posx/Global.TICKS_PER_PLAYER_MOVE*100+300),
        posy*4+44 - ((int)(1.0*posz/Global.TICKS_PER_PLAYER_MOVE*Global.MAX_PLAYER_JUMP)),
        50,50);
    }

    void repaint(Graphics g,Controller controller)
    {
        Rectangle rect = transRectangle();
        controller.dynamic_draw(g, myImage, 0,rect.x, rect.y, rect.width, rect.height);
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
