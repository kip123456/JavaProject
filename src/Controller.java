import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.sampled.Clip;
import java.util.*;


public class Controller {
    UIMode lastMode = UIMode.HOME;
    UIMode uiMode = UIMode.HOME;
    Lock ui_lock = new ReentrantLock();

    UI ui;

    /**
     * 0 没有结束
     * 1 玩家胜利
     * 2 玩家失败
     */
    int gameover = 0,bossHel=1000,maxbossHel=1000;
    Lock wasd_lock = new ReentrantLock();
    char wKey = ' ' , adKey = ' ', fKey = ' ';
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
    int gamemode=0;
    Random rand = new Random(System.currentTimeMillis());

    
    int ticks_already = 0;
    public Controller() {
        super();
        ui = new UI(this);
    }

    UIMode getUIMode() {
        ui_lock.lock();
        UIMode mode = uiMode;
        ui_lock.unlock();
        return mode;
    }
    void setUIMode(UIMode mode) {
        ui_lock.lock();
        if(uiMode != mode)
            lastMode = uiMode;
        uiMode = mode;
        ui_lock.unlock();
    }

    UIMode getLastUiMode() {
        ui_lock.lock();
        UIMode mode = lastMode;
        ui_lock.unlock();
        return mode;
    }
    
    void work() {
        while(true) {
            UIMode mode = getUIMode();
            ui.setMode(mode);
            switch(mode) {
                case HOME:
                    break;
                case GAMEING:
                    gamework();
                    break;
                case MANUAL:
                    break;
                default:
                    break;
            }
        }
    }

    void gamework() {
        DataManager.bgm[1].start();
        DataManager.bgm[1].loop(Clip.LOOP_CONTINUOUSLY);        
        while(gameover == 0 && getUIMode() == UIMode.GAMEING) {
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

    /*
     * 技能释放
     */
    int damage_delay,channelbuffer;
    void damageCaculate()
    {
        int damage = player.attack*player.skill_damage_rate;
        for(int i=0;i<things.size();++i)
        {
            Thing x = things.get(i);
            if(x.posx != channelbuffer)continue;
            if(x instanceof Monster)
            {
                damage = ((Monster)x).getDamage(damage,animation);
                if(damage == -1)
                {
                    break;
                }
                player.react(((Monster)x).died());
                things.remove(x);
                --i;
            }
        }
        if(damage > 0)
        {
            animation.new_animation(1,400, 100, 144, 144,5, DataManager.animationImg[3],1);    
            bossHel -= damage;
        }
    }
    void useSkill(int skillChannel){
        Rectangle rec = player.transRectangle();
        animation.new_animation(1, rec.x-50,rec.y-75,150,150,7,DataManager.animationImg[1],1);
        animation.new_animation(2, rec.x-15,70, 80, 580, 4, DataManager.animationImg[2],0);
        seController.add(DataManager.se[2]);
        channelbuffer = skillChannel;
        damage_delay = 12;
    }
    /**
     * 角色移动&技能
     */
    void moveCharacter() {
        int skillChannel=-1;
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
        if(fKey == 'f'){
            skillChannel = player.useSkill();
        }
        player.move(seController);
        wKey = adKey = fKey = ' ';
        wasd_lock.unlock();
        if(skillChannel!=-1)
        {
            useSkill(skillChannel);
        }
        if(damage_delay>0)
        {
            --damage_delay;
            if(damage_delay == 0)
            damageCaculate();
        }
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
                    animation.new_animation(1, rec.x, rec.y, rec.width, rec.height,3, DataManager.animationImg[0],1);    
                    seController.add(DataManager.se[0]);
                }
                else
                {
                    seController.add(DataManager.se[1]);
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
        if(gamemode == 0)return;

        if(player.health <=0) {
            System.out.println("Game Over!");
            gameover = 2;
        }
        else if(bossHel <= 0) {
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
    void animationpaint(Graphics g,int mode)
    {
        animation.paint(g,mode);
    }
    void bossHelBar(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.setColor(Color.black);
        g.drawRect(270, 120,404,15);
        g.setColor(Color.red);
        g.fillRect(270, 120,(int)(1.0*bossHel/maxbossHel*404),15);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
