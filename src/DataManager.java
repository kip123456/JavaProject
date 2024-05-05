import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class DataManager{
    static public int monster_num;
    static public int[][] monster_data;
    static public BufferedImage[] monster_img;
    static public BufferedImage[] icon;
    static public BufferedImage[] backgroundImg;
    static public BufferedImage[] animationImg;
    static public BufferedImage[][] reward_img;
    static public BufferedImage player_img;
    static public BufferedImage[] player_imgs;
    static public Clip[] bgm;
    static public File[] se;

    static{
        String path = Path.monsterData;
        
        try(Scanner reader = new Scanner(new File(path),"UTF-8")){
            monster_num = reader.nextInt();
            monster_data = new int[monster_num][6];
            for (int i = 0;i < monster_num;++i){
                for (int j = 0; j < 6; ++ j){
                    monster_data[i][j] = reader.nextInt();
                }
            }
        }catch (Exception e) {
            System.out.println("monster data read error");
        }

        path = Path.monsterImg;
        try{
            monster_img = new BufferedImage[monster_num];
            for(int i=0;i<monster_num;++i){
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

        path = Path.rewardImg;
        try{
            reward_img = new BufferedImage[4][10];
            //atk def mdef
            for(int i=1;i<=3;++i){
                reward_img[i][0] = ImageIO.read(new File(path+"/103-0"+i+".png"));
            }
            //hel
            for(int i=5;i<=8;++i){
                reward_img[0][i-5] = ImageIO.read(new File(path+"/103-0"+i+".png"));
            }
        }catch(Exception e){
            System.out.println("reward img read error");
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