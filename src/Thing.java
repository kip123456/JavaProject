import java.awt.Graphics;

public abstract class Thing {
    protected int posx,posy;
    public void move()
    {
        posy++;
    }
    abstract T2PMessage interact(Player p);
    public void repaint(Graphics g)
    {
        
    }
}