import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
            if(posx > 0) posx --;
            ++lrSteps;
        }
        if(lrSteps >0) {
            if(posx < (Global.CHANNEL_COUNT-1)*Global.CHANNEL_WIDTH) posx ++;
            --lrSteps;
        }
        if(udSteps <0) {
            if(posz>0) posz --;
            ++udSteps;
        }
        if(udSteps >0) {
            if(posz < Global.MAX_PLAYER_JUMP) posz ++;
            --udSteps;
        }
    }

    Rectangle transRectangle() {//to do : change the moving speed
        return new Rectangle(posx*25+325,posy*4+44 - posz*4,50,50);
    }

    void repaint(Graphics g)
    {
        Rectangle rect = transRectangle();
        g.drawImage(myImage, rect.x, rect.y, rect.width, rect.height, null);
    }

}
