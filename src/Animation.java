import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Animation {
    class animation {
        int every_last_time,nowatime,x,y,width,height,period,total_period,mode;
        //mode = 0/1 ：在透视变换前/后进行绘制
        BufferedImage myImage;
        animation(int a,int b,int c,int d,int e,int f,int ff,BufferedImage g,int _mode)
        {
            every_last_time=a;
            nowatime = b;
            x = c;
            y = d;
            width = e;
            height = f;
            period = 0;
            total_period = ff;
            myImage = g;
            mode = _mode;
        }
    };
    List<animation> list = new LinkedList<animation>();
    //assert : total_last_time is even
    public void new_animation(int every_last_time,int x,int y,int width,int height,int total_period,BufferedImage myImage,int mode)
    {
        list.add(new animation(every_last_time,0, x, y, width, height, total_period,myImage,mode));
    }
    public void paint(Graphics g,int mode)
    {
        Graphics2D g2d = (Graphics2D) g;
        for(animation e : list)
        {
            if(e.mode != mode)continue;
            int imgwidth = e.myImage.getWidth();
            int everywidth = imgwidth/e.total_period;
            g2d.drawImage(e.myImage,e.x,e.y,e.x+e.width,e.y+e.height,e.period*everywidth,0,(e.period+1)*everywidth,e.myImage.getHeight(), null);
            ++e.nowatime;
            if(e.nowatime==e.every_last_time)
            {
                e.nowatime = 0;
                e.period ++;
                if(e.period==e.total_period)
                list.remove(e);
            }
        }
    }
}
