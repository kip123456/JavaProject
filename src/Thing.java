import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public abstract class Thing {
    protected int posx,posy;
    BufferedImage myImage;
    boolean haveHeight=false;
    public void move()
    {
        posy+=Global.things_moving_speed;
    }
    abstract T2PMessage interact(Player p);
    abstract public void repaint(Graphics g,Controller controller);
    public Rectangle transPos()
    {
        return new Rectangle(100*posx+300,4*posy+44,100,60);
    }
}