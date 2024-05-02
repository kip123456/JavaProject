import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class DataManager{
    static public int monster_num;
    static public int[][] monster_data;
    static public BufferedImage[] monster_img;
    static public BufferedImage[][] reward_img;
    static{
        String path = Path.monsterData;
        
        try(Scanner reader = new Scanner(new File(path),"UTF-8")){
            monster_num = reader.nextInt();
            monster_data = new int[monster_num][4];
            for (int i = 0;i < monster_num;++i){
                for (int j = 0; j < 4; ++ j){
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
    }
}