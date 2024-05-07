package com.data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.Global;

public class DataManager{
    /*
     * 为总怪物数量，Global为怪物图片数量
     */
    static public int monster_num;
    /*
     * 第二维数据依次为：
     * 0 ： 血
     * 1 ： 攻
     * 2 ： 防
     * 3 ： 金
     * 4 ： 经
     * 5 ： 特性
     *  采用二进制方式存储，其中：
     *  1 ： 浮空
     *  2 ： 先攻
     *  4 ： 三连击
     *  8 ： 魔攻
     *  16 ： 坚固
     *  32 ： 破甲（战前附带角色防御90%作为伤害）
     *  64 ： 模仿（当角色属性更高时，属性等于角色属性）
     *  128 ：贯杀炮（第10回合造成攻击力1000%的伤害）
     *  256 : 怨念堆积（攻击+=仇恨值）
     *  512 ：怨念发酵（造成仇恨值的真伤，并使仇恨值翻倍，当被技能杀死后，仇恨值减半）
     *  1024 : 残虐（当血量低于怪物血量时，伤害翻倍）
     *  2048 : 净化（造成角色魔防两倍的伤害）
     * 6 ： 行走图的第几行
     */
    static public int[][] monster_data;
    static public BufferedImage[] monster_img;
    static public BufferedImage[] icon;
    static public BufferedImage[] backgroundImg;
    static public BufferedImage[] animationImg;
    /*
     * 第一维：血攻防魔防
     * 第二维：数值，以2的幂增长
     */
    static public BufferedImage[][] reward_img;
    static public BufferedImage player_img;
    static public BufferedImage[] player_imgs;
    static public Clip[] bgm;
    static public File[] se;

    static{
        String path = Path.monsterData;
        
        try(Scanner reader = new Scanner(new File(path),"UTF-8")){
            monster_num = Global.monsternum*4;
            monster_data = new int[monster_num][7];
            for (int i = 0;i < monster_num;++i){
                for (int j = 0; j < 6; ++ j){
                    monster_data[i][j] = reader.nextInt();
                }
                monster_data[i][6] = i%4;
            }
        }catch (Exception e) {
            System.out.println("monster data read error");
        }

        path = Path.monsterImg;
        try{
            monster_img = new BufferedImage[Global.monsternum];
            for(int i=0;i<Global.monsternum;++i){
                monster_img[i] = ImageIO.read(new File(path+"/"+i+".png"));
            }
        }catch(Exception e){
            System.out.println("monster img read error");
        }

        path = Path.iconImg;
        try{
            icon = new BufferedImage[Global.iconnum];
            for(int i=0;i<Global.iconnum;++i){
                icon[i] = ImageIO.read(new File(path+"/"+i+".png"));
            }
        }catch(Exception e){
            System.out.println("icon img read error");
        }

        path = Path.backgroundImg;
        try{
            backgroundImg = new BufferedImage[Global.backgroundnum];
            for(int i=0;i<Global.backgroundnum;++i){
                backgroundImg[i] = ImageIO.read(new File(path+"/"+i+".png"));
            }
        }catch(Exception e){
            System.out.println("background img read error");
        }

        path = Path.animationImg;
        try{
            animationImg = new BufferedImage[Global.animationnum];
            for(int i=0;i<Global.animationnum;++i){
                animationImg[i] = ImageIO.read(new File(path+"/"+i+".png"));
            }
        }catch(Exception e){
            System.out.println("animation img read error");
        }

        path = Path.rewardImg;
        try{
            reward_img = new BufferedImage[4][20];
            BufferedImage readbuffer;
            readbuffer = ImageIO.read(new File(path+"/4.png"));
            int _width = readbuffer.getWidth()/4,_height = readbuffer.getHeight()/4;
            for(int i=0;i<8;++i)
            reward_img[0][i] = readbuffer.getSubimage((i%4)*_width, (i/4)*_height, _width, _height);
            for(int i=0;i<4;++i){
                readbuffer = ImageIO.read(new File(path+"/"+i+".png"));
                for(int j = 0; j < 4 ; ++j)
                {
                    for(int k = 0; k< 3; ++ k)
                    {
                        reward_img[k+1][j+i*4] = readbuffer.getSubimage(k*_width, j*_height, _width, _height);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("reward img read error");
        }

        path = Path.bgm;
        try{
            bgm = new Clip[Global.bgmnum];
            for(int i=0;i<Global.bgmnum;++i){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(path+"/"+i+".wav"));
                bgm[i] = AudioSystem.getClip();
                bgm[i].open(audioInput);
            }
        }catch(Exception e){
            System.out.println("bgm load error");
        }

        path = Path.se;
        try{
            se = new File[Global.senum];
            for(int i=0;i<Global.senum;++i){
                se[i] = new File(path+"/"+i+".wav");
            }
        }catch(Exception e){
            System.out.println("bgm load error");
        }

        
        path = Path.playerImg;
        try{
            player_img = ImageIO.read(new File(path));
            player_imgs = new BufferedImage[4];
            player_imgs[3] = ImageIO.read(new File("./data/img/Player2.png"));
            player_imgs[2] = ImageIO.read(new File("./data/img/Player3.png"));
            player_imgs[1] = ImageIO.read(new File("./data/img/Player2.png"));
            player_imgs[0] = ImageIO.read(new File("./data/img/Player.png"));
        }catch(Exception e){
            System.out.println("player img read error");
        }
    }
}