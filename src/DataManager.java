import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class DataManager{
    static public int monster_num;
    static public int[][] monster_data;
    static public BufferedImage[] monster_img;
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
            for(int i=0;i<monster_num;++i){
                monster_img[i] = ImageIO.read(new File(path+"/"+i+".png"));
            }
        }catch(Exception e){
            System.out.println("monster img read error");
        }
    }
}