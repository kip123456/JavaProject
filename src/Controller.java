import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.sampled.Clip;
import javax.xml.crypto.Data;

import java.util.concurrent.locks.Condition;
import java.util.*;


public class Controller {

    UI ui;

    /**
     * 0 没有结束
     * 1 玩家胜利
     * 2 玩家失败
     */
    int gameover = 0;
    Lock wasd_lock = new ReentrantLock();
    char wKey = ' ' , adKey = ' ';
    Animation animation = new Animation();
    SEController seController = new SEController();
    

    /**
     * 角色
     */
    Player player = new Player();

    /**
     * 存放滑块的集合
     */
    List<Thing> things = new LinkedList<>();

    /**
     * 0: 无尽模式
     * 否则: 其他模式
     */
    int gamemode = 0;
    Random rand = new Random(System.currentTimeMillis());

    
    int ticks_already = 0;
    public Controller() {
        super();
        ui = new UI(this);
    }
    

    void work() {
        DataManager.bgm[1].start();
        DataManager.bgm[1].loop(Clip.LOOP_CONTINUOUSLY);        
        while(gameover == 0) {
            moveThing();
            removeThings();
            generateThings();
            moveCharacter();
            intereact();
            judge();
            repaint();
            ++ticks_already;
            try{
                Thread.sleep(1000/Global.TICK_PER_SEC);
            }catch(Exception e){}
        }
    }

    /**
     * 产生新的滑块
     */
    int lst_gen = 0;
    void generateThings(int road_id)
    {
        int random_num = rand.nextInt(100)+1;//[1,100]
        if(random_num<=6)
        {
            things.add(new Rewards(random_num/2, 0,road_id,0));
        }//atk or def or mdef generate
        else if(random_num<=10)
        {
            things.add(new Rewards(0, 0,road_id,0));
        }//hel generate
        else
        {
            things.add(new Monster((random_num-11)/(90/DataManager.monster_num), road_id, 0));
        }//monster generate
    }
    void generateThings() {
        if(lst_gen != 0)
        {
            --lst_gen;
            return;
        }
        lst_gen = Global.GENERATE_PADDING;
        if(gamemode == 0)
        {
            int random_num = rand.nextInt(15);
            for(int i=0,j=1;i<=3;++i,j<<=1)
            {
                if((j&random_num)>0)
                {
                    generateThings(i);
                }
            }
        }
        else
        {

        }
    }

    /**
     * 去掉一些已经离开玩家的滑块
     */
    void removeThings() {
        for(int i=0;i<things.size();++i) {
            Thing thing = things.get(i);
            if(thing.transPos().getY() > player.transRectangle().getY()) {
                things.remove(i);
                --i;
            }
        }
    }

    /**
     * 移动滑块
     */
    void moveThing() {
        for(Thing thing : things) {
            thing.move();
        }
    }


    /**
     * 角色移动
     */
    void moveCharacter() {
        wasd_lock.lock();
        if (wKey == 'w') {
            player.setMove(MovingState.UP);
        }
        if(adKey == 'a'){
            player.setMove(MovingState.LEFT);
        }
        if(adKey == 'd'){
            player.setMove(MovingState.RIGHT);
        }
        player.move();
        wKey = adKey = ' ';
        wasd_lock.unlock();
    }

    boolean meet(Player player, Thing thing) {
        if((player.udSteps != 0 || player.posz > 0 )&& thing.haveHeight==false)
            return false;
        Rectangle rec = thing.transPos();
        rec.height -= 20;
        return player.transRectangle().intersects(rec);
    }

    /**
     * 角色与滑块交互
     */
    void intereact() {
        for(int i=0;i<things.size();++i) {
            Thing thing = things.get(i);
            if(meet(player, thing)) {
                // 角色与滑块交互
                if(thing instanceof Monster)
                {
                    Rectangle rec = player.transRectangle();
                    animation.new_animation(1, rec.x, rec.y, rec.width, rec.height,3, DataManager.animationImg[0]);    
                    seController.add(DataManager.se[0]);
                }
                player.react(thing.interact(player));
                things.remove(i);
                --i;
            }
        }
    }
 

    /**
     * 判断游戏是否结束（或者之类的？）
     */
    void judge() {
        int all_ticks = 10;
        switch (gamemode) {
            case 0:
                all_ticks = ticks_already + 1000;
                break;
            default:
                all_ticks = 100000;
                break;
        }

        if(player.health <=0) {
            System.out.println("Game Over!");
            gameover = 2;
        }
        else if(ticks_already >= all_ticks) {
            System.out.println("Game Over!");
            gameover = 1;
        }
    }


    /*
     * 画行走图img的第id行，画到左上角在(x,y)
     */
    public void dynamic_draw(Graphics g,BufferedImage img,int id,int x,int y,int width,int height)
    {
        int rol = (ticks_already/10)%4;
        int imgwidth = img.getWidth()/4;
        int imgheight = img.getHeight()/4;
        g.drawImage(img,x,y,x+width,y+height,rol*imgwidth,id*imgheight,rol*imgwidth+imgwidth,id*imgheight+imgheight, null);
    }
    /**
     * 重绘ui
     */
    void repaint() {
        ui.repaint();
    }
    void animationpaint(Graphics g)
    {
        animation.paint(g);
    }
}
