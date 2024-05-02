import java.awt.Graphics;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.*;


public class Controller {

    UI ui;

    Lock wasd_lock;
    char wasd = ' ';

    /**
     * 角色
     */
    Player player;

    /**
     * 存放滑块的集合
     */
    List<Thing> things;

    int gamemode = 0;
    Random rand = new Random(System.currentTimeMillis());

    void work() {
        while(true) {
            moveThing();
            generateThings();
            moveCharacter();
            intereact();
            judge();
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
        if(random_num<=2)
        {
            things.add(new Rewards(random_num/2, 0,road_id,0));
        }//atk or def or mdef generate
        else if(random_num<=10)
        {
            things.add(new Rewards(random_num/2, 0,road_id,0));
        }//hel generate
        else
        {
            things.add(new Monster((random_num-10)/(90/DataManager.monster_num), road_id, 0));
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
        switch (wasd) {
            case 'w':
                player.move(Global.MovingState.UP);
                break;
            case 's':
                player.move(Global.MovingState.DOWN);
                break;
            case 'a':
                player.move(Global.MovingState.LEFT);
                break;
            case 'd':
                player.move(Global.MovingState.RIGHT);
                break;
            default:
                player.move(Global.MovingState.STOP);
                break;
        }
        wasd_lock.unlock();
    }

    boolean meet(Player player, Thing thing) {
        return Math.abs(player.transRectangle().getX() - thing.transPos().getX()) < 10
            && Math.abs(player.transRectangle().getY() - thing.transPos().getY()) < 10;
    }

    /**
     * 角色与滑块交互
     */
    void intereact() {
        for(Thing thing : things) {
            if(meet(player, thing)) {
                // 角色与滑块交互
                player.react(thing.interact(player));
            }
        }
    }
 

    /**
     * 判断游戏是否结束（或者之类的？）
     */
    void judge() {

    }


    /**
     * 重绘ui
     */
    void repaint() {
        ui.repaint();
    }
}
