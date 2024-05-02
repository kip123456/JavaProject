import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Thing {
    protected int posx,posy;
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
        
    }
}