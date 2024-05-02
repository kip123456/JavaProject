import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
    char wasd = ' ';

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
        if(player.udSteps!=0||player.lrSteps!=0)
        {
            player.move(MovingState.STOP);
        }
        else switch (wasd) {
            case 'w':
                    player.move(MovingState.UP);
                break;
            case 's':
                    player.move(MovingState.DOWN);
                break;
            case 'a':
                    player.move(MovingState.LEFT);
                break;
            case 'd':
                    player.move(MovingState.RIGHT);
                break;
            default:
                break;
        }
        wasd = ' ';
        wasd_lock.unlock();
    }

    boolean meet(Player player, Thing thing) {
        return player.transRectangle().intersects(thing.transPos());
    }

    /**
     * 角色与滑块交互
     */
    void intereact() {
        for(int i=0;i<things.size();++i) {
            Thing thing = things.get(i);
            if(meet(player, thing)) {
                // 角色与滑块交互
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


    /**
     * 重绘ui
     */
    void repaint() {
        ui.repaint();
    }
}
