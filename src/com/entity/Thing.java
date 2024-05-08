package com.entity;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.Controller;
import com.Global;

public abstract class Thing {
    public int posx,posy;
    public BufferedImage myImage;
    public boolean haveHeight=false;
    public void move()
    {
        posy+=Global.things_moving_speed;
    }
    public abstract T2PMessage interact(Player p);
    abstract public void repaint(Graphics g,Controller controller);
    public Rectangle transPos()
    {
        return new Rectangle(100*posx+300,4*posy+44,100,60);
    }

}