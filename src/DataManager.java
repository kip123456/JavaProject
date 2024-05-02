import java.io.File;
import java.util.Scanner;

public class DataManager{
    static public int monster_num;
    static public int[][] monster_data;

    static{
        String path = Path.monsterData;
        
        try(Scanner reader = new Scanner(new File(path),"UTF-8")){
            monster_num = reader.nextInt();
            monster_data = new int[monster_num+1][4];
            for (int i = 1;i <= monster_num;++i)
            {
                for (int j = 0; j < 4; ++ j)
                {
                    monster_data[i][j] = reader.nextInt();
                }
            }
        }catch (Exception e) {
            System.out.println("monster data read error");
        }
    }
}