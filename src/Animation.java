import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Animation {
    class animation {
        int total_last_time,nowatime,x,y,width,height;
        BufferedImage myImage;
        animation(int a,int b,int c,int d,int e,int f,BufferedImage g)
        {
            total_last_time=a;
            nowatime = b;
            x = c;
            y = d;
            width = e;
            height = f;
            myImage = g;
        }
    };
    List<animation> list = new LinkedList<animation>();
    //assert : total_last_time is even
    public void new_animation(int total_last_time,int x,int y,int width,int height,BufferedImage myImage)
    {
        list.add(new animation(total_last_time,0, x, y, width, height, myImage));
    }
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        for(animation e : list)
        {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f-Math.abs(e.nowatime*1.0f/(e.total_last_time/2)-1)));
            g2d.drawImage(e.myImage,e.x,e.y,e.width,e.height, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            ++e.nowatime;
            if(e.nowatime==e.total_last_time)
            list.remove(e);
        }
    }
}
