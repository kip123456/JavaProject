import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Thing {
    protected int posx,posy;
    BufferedImage myImage;
    public void move()
    {
        posy++;
    }
    abstract T2PMessage interact(Player p);
    public Rectangle transPos()
    {
        return new Rectangle(100*posx+300,4*posy+44,100,60);
    }
    public void repaint(Graphics g)
    {
        Rectangle rect = transPos();
        g.drawImage(myImage, rect.x, rect.y, rect.width, rect.height, null);
    }
}