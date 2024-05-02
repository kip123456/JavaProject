import java.awt.Graphics;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util. *;


public class Controller {

    UI ui;

    /**
     * 0 没有结束
     * 1 玩家胜利
     * 2 玩家失败
     */
    int gameover = 0;
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

    /**
     * 0: 无尽模式
     * 否则: 其他模式
     */
    int gamemode = 0;

    
    int ticks_already = 0;

    void work() {
        while(!gameover) {
            moveThing();
            generateThings();
            moveCharacter();
            intereact();
            judge();
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
        
    }
    void generateThings() {
        if(lst_gen != 0)
        {
            --lst_gen;
            return;
        }
        lst_gen = Global.GENERATE_PADDING;
        Random rand = new Random();
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


    /**
     * 重绘ui
     */
    void repaint() {
        ui.repaint();
    }
}
